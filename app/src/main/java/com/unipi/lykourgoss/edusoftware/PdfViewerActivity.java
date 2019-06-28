package com.unipi.lykourgoss.edusoftware;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.unipi.lykourgoss.edusoftware.models.Subsection;
import com.unipi.lykourgoss.edusoftware.viewmodels.SubsectionsViewModel;

import java.util.List;

public class PdfViewerActivity extends AppCompatActivity
        implements Observer<List<Subsection>>, OnLoadCompleteListener, OnPageChangeListener {

    private PDFView pdfView;

    private SubsectionsViewModel viewModel;

    private Subsection subsection;

    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_viewer);

        pdfView = findViewById(R.id.pdf_view);

        viewModel = ViewModelProviders.of(this).get(SubsectionsViewModel.class);
        viewModel.setParentId(getIntent().getStringExtra(Constant.EXTRA_PARENT_ID));
        viewModel.getAll().observe(this, this);
    }

    private void loadPdf() {
        if (subsection != null) {
            setTitle(subsection.getTitle());
            String path = String.format("subsections/%s/%s", subsection.getParentId(), subsection.getPdfFilename());
            pdfView.fromAsset(path)
                    .onPageChange(this)
                    .scrollHandle(new DefaultScrollHandle(this))
                    .onLoad(this)
                    .load();
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
        menuInflater.inflate(R.menu.subsection_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_subsection_details:
                // todo Dialog.showSubsectionDetails(getActivity(), isEditEnabled, subsection, this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
