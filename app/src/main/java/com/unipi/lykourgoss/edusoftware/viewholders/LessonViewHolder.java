package com.unipi.lykourgoss.edusoftware.viewholders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unipi.lykourgoss.edusoftware.adapters.OnItemClickListener;
import com.unipi.lykourgoss.edusoftware.models.Lesson;
import com.unipi.lykourgoss.edusoftware.R;

public class LessonViewHolder extends RecyclerView.ViewHolder {

    private OnItemClickListener listener;

    private Lesson lesson;

    private TextView textViewTitle;
    private TextView textViewIndex;
    private TextView textViewDescription;

    public LessonViewHolder(@NonNull final View itemView) {
        super(itemView);

        textViewTitle = itemView.findViewById(R.id.text_view_lesson_title);
        textViewIndex = itemView.findViewById(R.id.text_view_lesson_index);
        textViewDescription = itemView.findViewById(R.id.text_view_lesson_description);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = getAdapterPosition();
                // compare it to RecyclerView.NO_POSITION to make sure that it is still valid
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(lesson);
                }
            }
        });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int position = getAdapterPosition();
                // compare it to RecyclerView.NO_POSITION to make sure that it is still valid
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemLongClick(lesson);
                }
                return true;
            }
        });
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public void setItem(Lesson lesson) {
        this.lesson = lesson;
        textViewTitle.setText(lesson.getTitle());
        textViewIndex.setText(String.valueOf(lesson.getIndex()));
        textViewDescription.setText(lesson.getDescription());
    }

    // todo see if not needed
    public Lesson getItem(){
        return lesson;
    }
}