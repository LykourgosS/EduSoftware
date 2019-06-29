package com.unipi.lykourgoss.edusoftware;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.unipi.lykourgoss.edusoftware.viewmodels.QuestionViewModel;

public class ChapterExamActivity extends AppCompatActivity {

    private QuestionViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_exam);
    }
}
