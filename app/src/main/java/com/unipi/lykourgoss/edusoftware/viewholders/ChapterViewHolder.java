package com.unipi.lykourgoss.edusoftware.viewholders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.unipi.lykourgoss.edusoftware.R;
import com.unipi.lykourgoss.edusoftware.models.Chapter;

/**
 * Created by LykourgosS <lpsarantidis@gmail.com>
 * on 25,June,2019.
 */

public class ChapterViewHolder extends MyViewHolder <Chapter> {

    private TextView textViewTitle;

    private TextView textViewIndex;

    private TextView textViewDescription;

    public ChapterViewHolder(@NonNull View itemView) {
        super(itemView);

        textViewTitle = itemView.findViewById(R.id.text_view_chapter_title);
        textViewIndex = itemView.findViewById(R.id.text_view_chapter_index);
        textViewDescription = itemView.findViewById(R.id.text_view_chapter_description);
    }

    @Override
    public void setItem(Chapter chapter) {
        super.setItem(chapter);

        // todo if all entities has the same fields on item layout move declaration
        //  and initialization in parent class
        textViewTitle.setText(chapter.getTitle());
        textViewIndex.setText(String.valueOf(chapter.getIndex()));
        textViewDescription.setText(chapter.getDescription());
    }
}
