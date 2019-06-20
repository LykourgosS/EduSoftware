package com.unipi.lykourgoss.edusoftware.viewholders;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.unipi.lykourgoss.edusoftware.R;

public class LessonViewHolderOld extends RecyclerView.ViewHolder{

    public TextView textViewTitle;
    public TextView textViewIndex;
    public TextView textViewDescription;
    public TextView textViewLastModifiedBy;


    public LessonViewHolderOld(@NonNull View itemView) {
        super(itemView);

        textViewTitle = itemView.findViewById(R.id.text_view_lesson_title);
//        textViewTitle.setClickable(false);
        textViewIndex = itemView.findViewById(R.id.text_view_lesson_index);
//        textViewIndex.setClickable(false);
        textViewDescription = itemView.findViewById(R.id.text_view_lesson_description);
//        textViewDescription.setClickable(false);
        textViewLastModifiedBy = itemView.findViewById(R.id.text_view_lesson_lastModifiedBy);
//        textViewLastModifiedBy.setClickable(false);

        //textViewTime.setFocusable(false);
        //textViewTime.setClickable(true);
    }
}
