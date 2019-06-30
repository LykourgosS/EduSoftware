package com.unipi.lykourgoss.edusoftware;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.unipi.lykourgoss.edusoftware.models.Question;
import com.unipi.lykourgoss.edusoftware.viewmodels.QuestionViewModel;

import java.util.Collections;
import java.util.List;

public class ChapterExamActivity extends AppCompatActivity
        implements Observer<List<Question>>, View.OnClickListener {

    private QuestionViewModel viewModel;

    private String parentId; // chapter's id

    private String parentTitle; // chapter's title

    private AlertDialog dialog;

    private List<Question> tempQuestionList;

    private Question currentQuestion;

    private int score = 0;
    private int totalQuestionCount;

    private TextView textViewQuestionText;

    private RadioGroup radioGroupAnswers;

    private RadioButton radioButtonAnswer1;
    private RadioButton radioButtonAnswer2;
    private RadioButton radioButtonAnswer3;
    private RadioButton radioButtonAnswer4;

    private FloatingActionButton fabCheckAnswer;
    private FloatingActionButton fabNextQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_exam);

        dialog = Dialog.progressbarAction(this, Dialog.LOADING);

        textViewQuestionText = findViewById(R.id.text_view_exam_question);

        radioGroupAnswers = findViewById(R.id.radio_button_exam_answers);

        radioButtonAnswer1 = findViewById(R.id.radio_button_exam_answer_1);
        radioButtonAnswer1.setOnClickListener(this);

        radioButtonAnswer2 = findViewById(R.id.radio_button_exam_answer_2);
        radioButtonAnswer2.setOnClickListener(this);

        radioButtonAnswer3 = findViewById(R.id.radio_button_exam_answer_3);
        radioButtonAnswer3.setOnClickListener(this);

        radioButtonAnswer4 = findViewById(R.id.radio_button_exam_answer_4);
        radioButtonAnswer4.setOnClickListener(this);

        fabCheckAnswer = findViewById(R.id.fab_check_answer);
        fabCheckAnswer.setOnClickListener(this);
        fabNextQuestion = findViewById(R.id.fab_next_question);
        fabNextQuestion.setOnClickListener(this);

        Intent intent = getIntent();

        parentId = intent.getStringExtra(Constant.EXTRA_PARENT_ID);
        parentTitle = intent.getStringExtra(Constant.EXTRA_PARENT_TITLE);

        setTitle("Chapter Exam");
        getSupportActionBar().setSubtitle(parentTitle);

        viewModel = ViewModelProviders.of(this).get(QuestionViewModel.class);
        viewModel.setParentId(parentId);
        viewModel.getAll().observe(this, this);
    }

    @Override
    public void onChanged(List<Question> questions) {
        totalQuestionCount = questions.size();
        this.tempQuestionList = questions;
        Collections.shuffle(this.tempQuestionList);
        loadQuestion();
        dialog.cancel();
    }

    private void loadQuestion() {
        if (!tempQuestionList.isEmpty()) { // questions are not done
            currentQuestion = tempQuestionList.remove(0);
            textViewQuestionText.setText(currentQuestion.getQuestionText());
            List<String> answers = currentQuestion.getWrongAnswers();
            answers.add(currentQuestion.getCorrectAnswer());
            // shuffle answers
            Collections.shuffle(answers);
            radioButtonAnswer1.setText(answers.remove(0));
            radioButtonAnswer2.setText(answers.remove(0));
            radioButtonAnswer3.setText(answers.remove(0));
            radioButtonAnswer4.setText(answers.remove(0));

            radioGroupAnswers.check(-1);
        } else { // there are not any other question
            // todo dialog exam score and onClick on ok -> finish()
            Toast.makeText(this, String.format("Score %s/%s", score, totalQuestionCount), Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void checkCorrectRadioButton() {
        if (radioButtonAnswer1.getText() == currentQuestion.getCorrectAnswer()) {
            radioGroupAnswers.check(radioButtonAnswer1.getId());
        } else if (radioButtonAnswer2.getText() == currentQuestion.getCorrectAnswer()) {
            radioGroupAnswers.check(radioButtonAnswer2.getId());
        } else if (radioButtonAnswer3.getText() == currentQuestion.getCorrectAnswer()) {
            radioGroupAnswers.check(radioButtonAnswer3.getId());
        } else {
            radioGroupAnswers.check(radioButtonAnswer4.getId());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /*case R.id.radio_button_exam_answer_1:
                checkAnswer();
                return;
            case R.id.radio_button_exam_answer_2:
                checkAnswer();
                return;
            case R.id.radio_button_exam_answer_3:
                checkAnswer();
                return;
            case R.id.radio_button_exam_answer_4:
                checkAnswer();
                return;*/
            case R.id.fab_check_answer:
                if (radioGroupAnswers.getCheckedRadioButtonId() == -1) { // -1 means empty selection
                    Toast.makeText(this, "Select an answer first", Toast.LENGTH_SHORT).show();
                } else {
                    checkAnswer();
                    setUpButtons(true);
                }
                return;
            case R.id.fab_next_question:
                loadQuestion();
                setUpButtons(false);
                return;
        }
    }

    private void setUpButtons(boolean showNextButton) {
        if (showNextButton) {
            fabCheckAnswer.hide();
            fabNextQuestion.show();
        } else {
            fabCheckAnswer.show();
            fabNextQuestion.hide();
        }
    }

    private void checkAnswer() {
        int selectedRadioId = radioGroupAnswers.getCheckedRadioButtonId();

        RadioButton radioButton = findViewById(selectedRadioId);
        if (radioButton.getText() == currentQuestion.getCorrectAnswer()) {
            score += 1;
            Toast.makeText(this, "Correct answer", Toast.LENGTH_SHORT).show();
        } else {
            checkCorrectRadioButton();
            Toast.makeText(this, "Wrong answer", Toast.LENGTH_SHORT).show();
        }
    }
}
