package com.unipi.lykourgoss.edusoftware;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.unipi.lykourgoss.edusoftware.models.Subsection;
import com.unipi.lykourgoss.edusoftware.statistics.SubsectionStatistic.SubsectionStatistic;
import com.unipi.lykourgoss.edusoftware.statistics.SubsectionStatistic.SubsectionStatisticViewModel;
import com.unipi.lykourgoss.edusoftware.viewmodels.SubsectionsViewModel;

import java.io.File;
import java.util.List;

public class PdfViewerActivity extends AppCompatActivity
        implements Observer<List<Subsection>>, OnLoadCompleteListener, OnPageChangeListener, OnCompleteListener<FileDownloadTask.TaskSnapshot> {

    private PDFView pdfView;

    private SubsectionsViewModel viewModel;

    private Subsection subsection;

    private AlertDialog dialog;

    private StorageReference subsectionsReference;

    private File localFile;

    private SubsectionStatisticViewModel statisticViewModel;

    private long startTime; // used for counting time user has pdf open

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_viewer);

        statisticViewModel = ViewModelProviders.of(this).get(SubsectionStatisticViewModel.class);

        subsectionsReference = FirebaseStorage.getInstance().getReference().child("subsections");

        pdfView = findViewById(R.id.pdf_view);

        viewModel = ViewModelProviders.of(this).get(SubsectionsViewModel.class);
        viewModel.setParentId(getIntent().getStringExtra(Constant.EXTRA_PARENT_ID));
        viewModel.getAll().observe(this, this);
    }

    private void loadPdf() {
        if (subsection != null) {
            setTitle(subsection.getTitle());
            try{
                localFile = File.createTempFile(subsection.getPdfFilename(), null, this.getCacheDir());
                subsectionsReference.child(subsection.getId()).child(subsection.getPdfFilename())
                        .getFile(localFile)
                        .addOnCompleteListener(this);
            }catch (Exception e){
                dialog.cancel();
                Toast.makeText(this, "Error loading pdf", Toast.LENGTH_SHORT).show();
                finish();
            }
        } else {
            dialog.cancel();
            Toast.makeText(this, "Error loading pdf", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public void onChanged(List<Subsection> subsections) {
        dialog = Dialog.progressbarAction(this, Dialog.LOADING);
        String id = getIntent().getStringExtra(Constant.EXTRA_ID);
        for (Subsection subsection : subsections) {
            if (subsection.getId().equals(id)) {
                this.subsection = subsection;
                loadPdf();
                break;
            }
        }
    }

    @Override
    public void loadComplete(int nbPages) {
        dialog.cancel();
    }

    @Override
    public void onPageChanged(int page, int pageCount) {
        getSupportActionBar().setSubtitle(String.format("%s / %s", page + 1, pageCount));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.statistics_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_statistics:
                // todo statistics dialog
                return true;
            case R.id.menu_item_subsection_details:
                // todo Dialog.showSubsectionDetails(getActivity(), isEditEnabled, subsection, this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onComplete(@NonNull Task<FileDownloadTask.TaskSnapshot> task) {
        if (task.isSuccessful()) {

            startTime = SystemClock.elapsedRealtime();

            pdfView.fromFile(localFile)
                    .onPageChange(PdfViewerActivity.this)
                    .scrollHandle(new DefaultScrollHandle(PdfViewerActivity.this))
                    .onLoad(PdfViewerActivity.this)
                    .load();
        } else {
            dialog.cancel();
            Toast.makeText(PdfViewerActivity.this, "Error loading pdf", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    protected void onPause() {
        long leftOpen = SystemClock.elapsedRealtime() - startTime;
        SubsectionStatistic statistic = new SubsectionStatistic(subsection.getId(), subsection.getTitle(), subsection.getPdfFilename(), leftOpen);
        statisticViewModel.insert(statistic);
        super.onPause();
    }
}
