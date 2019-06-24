package com.unipi.lykourgoss.edusoftware.viewholders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.unipi.lykourgoss.edusoftware.R;
import com.unipi.lykourgoss.edusoftware.models.Lesson;

public class LessonViewHolder extends MyViewHolder<Lesson> {

    private TextView textViewTitle;
    private TextView textViewIndex;
    private TextView textViewDescription;

    public LessonViewHolder(@NonNull final View itemView) {
        super(itemView);

        textViewTitle = itemView.findViewById(R.id.text_view_lesson_title);
        textViewIndex = itemView.findViewById(R.id.text_view_lesson_index);
        textViewDescription = itemView.findViewById(R.id.text_view_lesson_description);
    }


    @Override
    public void setItem(Lesson lesson) {
        super.setItem(lesson);

        textViewTitle.setText(lesson.getTitle());
        textViewIndex.setText(String.valueOf(lesson.getIndex()));
        textViewDescription.setText(lesson.getDescription());
    }
}