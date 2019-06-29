package com.unipi.lykourgoss.edusoftware;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.unipi.lykourgoss.edusoftware.models.Question;
import com.unipi.lykourgoss.edusoftware.viewmodels.QuestionViewModel;

import org.w3c.dom.Text;

import java.util.List;

public class ChapterExamActivity extends AppCompatActivity implements Observer<List<Question>> {

    private QuestionViewModel viewModel;

    private String parentId; // chapter's id

    private String parentTitle; // chapter's title

    private AlertDialog dialog;

    private List<Question> questionList;

    private TextView textViewQuestionText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_exam);

        Intent intent = getIntent();

        parentId = intent.getStringExtra(Constant.EXTRA_PARENT_ID);
        parentTitle = intent.getStringExtra(Constant.EXTRA_PARENT_TITLE);

        viewModel = ViewModelProviders.of(this).get(QuestionViewModel.class);
        viewModel.setParentId(getIntent().getStringExtra(Constant.EXTRA_PARENT_ID));
        viewModel.getAll().observe(this, this);

        setTitle("Chapter Exam");
        getSupportActionBar().setSubtitle(parentTitle);

        dialog = Dialog.progressbarAction(this, Dialog.LOADING);
    }

    @Override
    public void onChanged(List<Question> questions) {
        dialog.cancel();
        this.questionList = questions;
    }
}
