package com.unipi.lykourgoss.edusoftware.adapters;

import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unipi.lykourgoss.edusoftware.R;
import com.unipi.lykourgoss.edusoftware.adapters.OnItemClickListener;
import com.unipi.lykourgoss.edusoftware.models.EduEntity;

/**
 * Created by LykourgosS <lpsarantidis@gmail.com>
 * on 25,June,2019.
 */

public class MyViewHolder <Model extends EduEntity> extends RecyclerView.ViewHolder {

    private OnItemClickListener<Model> listener;

    private Model model;

    private TextView textViewTitle;

    private TextView textViewIndex;

    private TextView textViewDescription;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        textViewTitle = itemView.findViewById(R.id.text_view_edu_entity_title);
        textViewIndex = itemView.findViewById(R.id.text_view_edu_entity_index);
        textViewDescription = itemView.findViewById(R.id.text_view_edu_entity_description);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = getAdapterPosition();
                // compare it to RecyclerView.NO_POSITION to make sure that it is still valid
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(model);
                }
            }
        });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int position = getAdapterPosition();
                // compare it to RecyclerView.NO_POSITION to make sure that it is still valid
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemLongClick(model);
                }
                return true;
            }
        });

        //initialize all the viewHolder views
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public void setItem(Model model) {
        this.model = model;
        // on child classes fill with model's values all of the viewHolder fields
        textViewTitle.setText(model.getTitle());
        textViewIndex.setText(String.valueOf(model.getIndex()));
        textViewDescription.setText(model.getDescription());
    }

    // todo see if not needed
    public Model getItem(){
        return model;
    }
}
