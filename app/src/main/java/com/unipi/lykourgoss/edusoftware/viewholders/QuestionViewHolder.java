package com.unipi.lykourgoss.edusoftware.viewholders;

import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.unipi.lykourgoss.edusoftware.R;
import com.unipi.lykourgoss.edusoftware.models.Question;

/**
 * Created by LykourgosS <lpsarantidis@gmail.com>
 * on 29,June,2019.
 */

public class QuestionViewHolder extends MyViewHolder<Question> {

    private EditText editTextQuestion;

    private EditText editTextCorrectAnswer;

    private EditText editTextWrongAnswer1;
    private EditText editTextWrongAnswer2;
    private EditText editTextWrongAnswer3;
    
    public QuestionViewHolder(@NonNull View itemView) {
        super(itemView);

        editTextQuestion = itemView.findViewById(R.id.create_edit_question);
        editTextQuestion.setFocusableInTouchMode(false);
        editTextQuestion.clearFocus();

        editTextCorrectAnswer = itemView.findViewById(R.id.create_edit_correct_answer);

        editTextWrongAnswer1 = itemView.findViewById(R.id.create_edit_wrong_answer_1);
        editTextWrongAnswer2 = itemView.findViewById(R.id.create_edit_wrong_answer_2);
        editTextWrongAnswer3 = itemView.findViewById(R.id.create_edit_wrong_answer_3);
    }

    @Override
    public void setItem(Question question) {
        this.item = question;
        editTextQuestion.setText(question.getQuestionText());

        editTextCorrectAnswer.setText(question.getCorrectAnswer());

        editTextWrongAnswer1.setText(question.getWrongAnswers().get(0));
        editTextWrongAnswer2.setText(question.getWrongAnswers().get(1));
        editTextWrongAnswer3.setText(question.getWrongAnswers().get(2));
    }
}
