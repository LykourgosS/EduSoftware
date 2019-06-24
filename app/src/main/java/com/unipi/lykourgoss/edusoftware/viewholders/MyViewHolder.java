package com.unipi.lykourgoss.edusoftware.viewholders;

import android.view.View;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unipi.lykourgoss.edusoftware.adapters.OnItemClickListener;
import com.unipi.lykourgoss.edusoftware.models.EduEntity;

/**
 * Created by LykourgosS <lpsarantidis@gmail.com>
 * on 25,June,2019.
 */

public abstract class MyViewHolder <Model extends EduEntity> extends RecyclerView.ViewHolder {

    protected OnItemClickListener<Model> listener;

    protected Model model;

    protected MyViewHolder(@NonNull View itemView) {
        super(itemView);

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
    }

    // todo see if not needed
    public Model getItem(){
        return model;
    }
}
