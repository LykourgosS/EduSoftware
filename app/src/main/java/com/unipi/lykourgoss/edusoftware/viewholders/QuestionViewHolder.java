package com.unipi.lykourgoss.edusoftware.viewholders;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.unipi.lykourgoss.edusoftware.R;
import com.unipi.lykourgoss.edusoftware.models.Question;

/**
 * Created by LykourgosS <lpsarantidis@gmail.com>
 * on 29,June,2019.
 */

public class QuestionViewHolder extends MyViewHolder<Question> {

    private TextView textViewQuestion;
    
    public QuestionViewHolder(@NonNull View itemView) {
        super(itemView);

        textViewQuestion = itemView.findViewById(R.id.item_question_text);
    }

    @Override
    public void setItem(Question question) {
        this.item = question;
        textViewQuestion.setText(question.getQuestionText());
    }
}
