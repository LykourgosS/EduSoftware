package com.unipi.lykourgoss.edusoftware.viewholders;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by LykourgosS <lpsarantidis@gmail.com>
 * on 25,June,2019.
 */

public abstract class MyViewHolder<Τ> extends RecyclerView.ViewHolder {

    protected OnItemClickListener<Τ> listener;

    protected Τ item;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = getAdapterPosition();
                // compare it to RecyclerView.NO_POSITION to make sure that it is still valid
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(item);
                }
            }
        });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int position = getAdapterPosition();
                // compare it to RecyclerView.NO_POSITION to make sure that it is still valid
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemLongClick(item);
                }
                return true;
            }
        });

        //initialize all the viewHolder views
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    // todo see if not needed
    public Τ getItem() {
        return item;
    }

    public abstract void setItem(Τ item);
}
