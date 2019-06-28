package com.unipi.lykourgoss.edusoftware.filestoremove;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.unipi.lykourgoss.edusoftware.Constant;
import com.unipi.lykourgoss.edusoftware.Dialog;
import com.unipi.lykourgoss.edusoftware.R;
import com.unipi.lykourgoss.edusoftware.models.Subsection;
import com.unipi.lykourgoss.edusoftware.viewmodels.SubsectionsViewModel;

import java.util.List;

public class PdfViewerActivityWebView extends AppCompatActivity implements Observer<List<Subsection>> {

    private WebView webView;

    private SubsectionsViewModel viewModel;

    private Subsection subsection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_viewer_webview);

        webView = findViewById(R.id.web_view);

        Intent intent = getIntent();

        if (intent.hasExtra(Constant.EXTRA_ID) && intent.hasExtra(Constant.EXTRA_PARENT_ID)) {
            viewModel = ViewModelProviders.of(this).get(SubsectionsViewModel.class);
            // todo check if getModel() works...
            viewModel.setParentId(getIntent().getStringExtra(Constant.EXTRA_PARENT_ID));
            viewModel.getAll().observe(this, this);
        } else {
            Toast.makeText(this, "Error loading pdf", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public void onChanged(List<Subsection> subsections) {
        AlertDialog dialogLoading = Dialog.progressbarAction(this, Dialog.LOADING);
        String id = getIntent().getStringExtra(Constant.EXTRA_ID);
        for (Subsection subsection : subsections) {
            if (subsection.getId().equals(id)) {
                this.subsection = subsection;
                loadPdf(dialogLoading);
                break;
            }
        }
    }

    private void loadPdf(final AlertDialog dialog) {
        if (subsection != null) {
            String url = "http://drive.google.com/viewerng/viewer?embedded=true&url=" + subsection.getPdfUrl();
            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setBuiltInZoomControls(true);
            webView.setWebChromeClient(new WebChromeClient() {
                @Override
                public void onProgressChanged(WebView view, int newProgress) {
                    super.onProgressChanged(view, newProgress);
                    if (newProgress == 100){
                        dialog.cancel();
                        setTitle(subsection.getTitle());
                    }
                }
            });
            webView.loadUrl(url);
        } else {
            dialog.cancel();
            Toast.makeText(this, "Error loading pdf", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
