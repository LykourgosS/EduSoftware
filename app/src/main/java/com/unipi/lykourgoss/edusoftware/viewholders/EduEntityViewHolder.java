package com.unipi.lykourgoss.edusoftware.viewholders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.unipi.lykourgoss.edusoftware.R;
import com.unipi.lykourgoss.edusoftware.models.EduEntity;

/**
 * Created by LykourgosS <lpsarantidis@gmail.com>
 * on 29,June,2019.
 */

public class EduEntityViewHolder<Model extends EduEntity> extends MyViewHolder<Model> {

    private TextView textViewTitle;
    private TextView textViewIndex;
    private TextView textViewDescription;

    public EduEntityViewHolder(@NonNull View itemView) {
        super(itemView);

        textViewTitle = itemView.findViewById(R.id.text_view_edu_entity_title);
        textViewIndex = itemView.findViewById(R.id.text_view_edu_entity_index);
        textViewDescription = itemView.findViewById(R.id.text_view_edu_entity_description);
    }

    @Override
    public void setItem(Model model) {
        this.item = model;
        // on child classes fill with model's values all of the viewHolder fields
        textViewTitle.setText(model.getTitle());
        textViewIndex.setText(String.valueOf(model.getIndex()));
        textViewDescription.setText(model.getDescription());
    }
}
