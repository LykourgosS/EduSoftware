package com.unipi.lykourgoss.edusoftware;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ProgressBar;
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
import com.unipi.lykourgoss.edusoftware.statistics.ExamStatistic.ExamStatistic;
import com.unipi.lykourgoss.edusoftware.statistics.ExamStatistic.ExamStatisticViewModel;
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

    // ui properties
    private ProgressBar progressBar;
    private TextView textViewScore;

    private Chronometer chronometer;
    private long startTime;

    private TextView textViewQuestionText;

    private RadioGroup radioGroupAnswers;

    private RadioButton radioButtonAnswer1;
    private RadioButton radioButtonAnswer2;
    private RadioButton radioButtonAnswer3;
    private RadioButton radioButtonAnswer4;

    private FloatingActionButton fabCheckAnswer;
    private FloatingActionButton fabNextQuestion;

    private ExamStatisticViewModel statisticViewModel;

    private List<ExamStatistic> statistics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_exam);

        statisticViewModel = ViewModelProviders.of(this).get(ExamStatisticViewModel.class);

        dialog = Dialog.progressbarAction(this, Dialog.LOADING);

        progressBar = findViewById(R.id.exam_progress_bar);
        textViewScore = findViewById(R.id.text_view_score);
        chronometer = findViewById(R.id.text_view_chronometer);

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

        setUpViewModel();
    }

    private void setUpViewModel() {
        statisticViewModel.getAll().observe(this, new Observer<List<ExamStatistic>>() {
            @Override
            public void onChanged(List<ExamStatistic> examStatistics) {
                statistics = examStatistics;
            }
        });
//        long b = statisticViewModel.getTotalTimeInMin(parentId);
    }


    // means if user leaves exam he will have to take it again
    @Override
    protected void onResume() {
        super.onResume();
        viewModel = ViewModelProviders.of(this).get(QuestionViewModel.class);
        viewModel.setParentId(parentId);
        viewModel.getAll().observe(this, this);
    }

    private void startChronometer() {
        chronometer.setBase(startTime);
        chronometer.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.statistics_menu, menu);
        return true;
    }

    private String statisticsMessage() {
        int totalTries = statisticViewModel.getTotalTries(parentId);
        int fail = statisticViewModel.getTotalFailOrPass(parentId, false);
        int pass = statisticViewModel.getTotalFailOrPass(parentId, true);
        long totalMin = statisticViewModel.getTotalTimeInMin(parentId);

        String message = "- Total times of taking the exam: " + totalTries;
        message += "\n\n- Failure percentage (%): " + (double) fail / totalTries;
        message += "\n\n- Success percentage (%): " + (double) pass / totalTries;
        message += "\n\n- Total amount of time spent in exam " + totalMin + " min";
        return message;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_statistics:
                String message = "";
                for (ExamStatistic statistic : statistics) {
                    message += statistic.toString() + "\n\n";
                }
                Dialog.simpleDialog(this, "Exam statistics", /*statisticsMessage()*/message);
                return true;
            case R.id.menu_item_subsection_details:
                // todo Dialog.showSubsectionDetails(getActivity(), isEditEnabled, subsection, this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onChanged(List<Question> questions) {
        this.totalQuestionCount = questions.size();
        progressBar.setMax(totalQuestionCount);

        this.tempQuestionList = questions;
        Collections.shuffle(this.tempQuestionList);
        loadQuestion();
        dialog.cancel();

        startTime = SystemClock.elapsedRealtime();
        startChronometer();
    }

    private void loadQuestion() {
        if (!tempQuestionList.isEmpty()) { // questions are not done

            progressBar.setProgress(progressBar.getProgress() + 1);

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
            textViewScore.setText(Integer.toString(score));
            Toast.makeText(this, "Correct answer", Toast.LENGTH_SHORT).show();
        } else {
            checkCorrectRadioButton();
            Toast.makeText(this, "Wrong answer", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
        long duration = SystemClock.elapsedRealtime() - startTime;
        ExamStatistic statistic = new ExamStatistic(parentId, parentTitle, score, totalQuestionCount, duration, tempQuestionList.isEmpty());
        statisticViewModel.insert(statistic);
        super.onPause();
    }
}
