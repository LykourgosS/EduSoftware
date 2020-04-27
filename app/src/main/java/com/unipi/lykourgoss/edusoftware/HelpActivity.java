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

public class HelpActivity extends AppCompatActivity
        implements OnLoadCompleteListener, OnPageChangeListener {

    private PDFView pdfView;

    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_viewer);

        pdfView = findViewById(R.id.pdf_view);

        setTitle("Help");

        dialog = Dialog.progressbarAction(this, Dialog.LOADING);

        String path = String.format("help.pdf");
        pdfView.fromAsset(path)
                .onPageChange(this)
                .scrollHandle(new DefaultScrollHandle(this))
                .onLoad(this)
                .load();
    }

    @Override
    public void loadComplete(int nbPages) {
        dialog.cancel();
    }

    @Override
    public void onPageChanged(int page, int pageCount) {
        getSupportActionBar().setSubtitle(String.format("%s / %s", page + 1, pageCount));
    }
}
