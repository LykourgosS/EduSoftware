package com.unipi.lykourgoss.edusoftware.activities.createedit;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.unipi.lykourgoss.edusoftware.Constant;
import com.unipi.lykourgoss.edusoftware.R;
import com.unipi.lykourgoss.edusoftware.models.Question;
import com.unipi.lykourgoss.edusoftware.viewmodels.MyViewModel;
import com.unipi.lykourgoss.edusoftware.viewmodels.QuestionViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CreateEditQuestionActivity extends CreateEditActivity implements Observer<List<Question>> {

    private EditText editTextQuestion;

    private EditText editTextCorrectAnswer;

    private EditText editTextWrongAnswer1;
    private EditText editTextWrongAnswer2;
    private EditText editTextWrongAnswer3;

    private QuestionViewModel viewModel;

    private String id;
    private String parentId; // chapter id to which the exam question belongs
    private int oldQuestionCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_edit_question);

        viewModel = ViewModelProviders.of(this).get(QuestionViewModel.class);
        viewModel.setParentId(getIntent().getStringExtra(Constant.EXTRA_PARENT_ID));
        viewModel.getAll().observe(this, this);

        editTextQuestion = findViewById(R.id.create_edit_question);

        editTextCorrectAnswer = findViewById(R.id.create_edit_correct_answer);

        editTextWrongAnswer1 = findViewById(R.id.create_edit_wrong_answer_1);
        editTextWrongAnswer2 = findViewById(R.id.create_edit_wrong_answer_2);
        editTextWrongAnswer3 = findViewById(R.id.create_edit_wrong_answer_3);

        Intent intent = getIntent();

        id = intent.getStringExtra(Constant.EXTRA_ID); // if attempting to create new id will be null
        oldQuestionCount = intent.getIntExtra(Constant.EXTRA_CHILD_COUNT, 0);
        parentId = intent.getStringExtra(Constant.EXTRA_PARENT_ID);
        String parentTitle = intent.getStringExtra(Constant.EXTRA_PARENT_TITLE);

        getSupportActionBar().setSubtitle(parentTitle);
    }

    @Override
    protected void saveEntity() {
        String questionText = editTextQuestion.getText().toString().trim();
        String correctAnswer = editTextCorrectAnswer.getText().toString().trim();

        String wrongAnswer1 = editTextWrongAnswer1.getText().toString().trim();
        String wrongAnswer2 = editTextWrongAnswer2.getText().toString().trim();
        String wrongAnswer3 = editTextWrongAnswer3.getText().toString().trim();

        List<String> wrongAnswerList = new ArrayList<>();
        wrongAnswerList.add(wrongAnswer1);
        wrongAnswerList.add(wrongAnswer2);
        wrongAnswerList.add(wrongAnswer3);

        if (questionText.isEmpty()) {
            editTextQuestion.setError("Question is required");
            editTextQuestion.requestFocus();
            return;
        } else if (correctAnswer.isEmpty()) {
            editTextCorrectAnswer.setError("Correct answer cannot be empty");
            editTextCorrectAnswer.requestFocus();
            return;
        } else if (wrongAnswer1.isEmpty()) {
            editTextWrongAnswer1.setError("Wrong answer cannot be empty");
            editTextWrongAnswer1.requestFocus();
            return;
        } else if (wrongAnswer2.isEmpty()) {
            editTextWrongAnswer2.setError("Wrong answer cannot be empty");
            editTextWrongAnswer2.requestFocus();
            return;
        } else if (wrongAnswer3.isEmpty()) {
            editTextWrongAnswer3.setError("Wrong answer cannot be empty");
            editTextWrongAnswer3.requestFocus();
            return;
        }
        Set<String> uniqueList = new HashSet<>(wrongAnswerList);
        if (uniqueList.size() != wrongAnswerList.size()){
            Toast.makeText(this, "Answers must be unique", Toast.LENGTH_SHORT).show();
            return;
        }

        Question question = new Question(id, questionText, correctAnswer, wrongAnswerList, parentId);
        viewModel.create(question, oldQuestionCount);
        Toast.makeText(this, "Question created", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onChanged(List<Question> questions) {
        loadQuestion(viewModel.getQuestionById(id, questions));
    }

    private void loadQuestion(Question question) {
        if (question != null) { // edit

            setTitle("Edit Question");

            editTextQuestion.setText(question.getQuestionText());

            editTextCorrectAnswer.setText(question.getCorrectAnswer());

            List<String> wrongAnswers = question.getWrongAnswers();
            editTextWrongAnswer1.setText(wrongAnswers.remove(0)); // each time remove first answer
            editTextWrongAnswer2.setText(wrongAnswers.remove(0));
            editTextWrongAnswer3.setText(wrongAnswers.remove(0));
        } else { // create new
            setTitle("Create New Question");
        }
    }
}
