package com.unipi.lykourgoss.edusoftware;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.unipi.lykourgoss.edusoftware.models.Subsection;
import com.unipi.lykourgoss.edusoftware.viewmodels.SubsectionsViewModel;

public class PdfViewerActivity extends AppCompatActivity implements OnLoadCompleteListener {

    PDFView pdfView;

    private SubsectionsViewModel viewModel;

    private Subsection subsection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_viewer);

        pdfView = findViewById(R.id.pdf_view);

        Intent intent = getIntent();
        if (intent.hasExtra(Constant.EXTRA_ID)) {
            String id = intent.getStringExtra(Constant.EXTRA_ID);
            viewModel = ViewModelProviders.of(this).get(SubsectionsViewModel.class);
            subsection = viewModel.getModel(id).getValue();
        }
        AlertDialog dialogLoading = Dialog.progressbarAction(this, Dialog.LOADING);
        loadPdf(dialogLoading);
    }

    private void loadPdf(AlertDialog dialog) {
        if (subsection != null){
            Uri uri = Uri.parse("http://docs.google.com/gview?embedded=true&url=" + subsection.getPdfUrl());
//            pdfView.fromUri(uri).onLoad(this).load();
        } else {
            dialog.cancel();
            Toast.makeText(this, "Error loading pdf", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public void loadComplete(int nbPages) {

    }
}
