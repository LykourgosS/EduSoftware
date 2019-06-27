package com.unipi.lykourgoss.edusoftware;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;

import java.net.URI;
import java.net.URISyntaxException;

public class PdfViewerActivity extends AppCompatActivity {

    PDFView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_viewer);

        pdfView = findViewById(R.id.pdf_view);

        /*try {
            pdfView.fromUri((new URI("https://drive.google.com/drive/folders/0B_w-zSSF5xpFc3VURjVmLUZuSlE")));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }*/
    }
}
