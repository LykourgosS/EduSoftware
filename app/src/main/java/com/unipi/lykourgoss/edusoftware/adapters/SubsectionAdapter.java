package com.unipi.lykourgoss.edusoftware.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.unipi.lykourgoss.edusoftware.R;
import com.unipi.lykourgoss.edusoftware.models.Subsection;
import com.unipi.lykourgoss.edusoftware.viewholders.EduEntityViewHolder;
import com.unipi.lykourgoss.edusoftware.viewholders.MyViewHolder;

/**
 * Created by LykourgosS <lpsarantidis@gmail.com>
 * on 25,June,2019.
 */

public class SubsectionAdapter extends MyAdapter<Subsection> {

    // static because we want to be initialised before
    // executing the constructor (in which we used it)
    private static final DiffUtil.ItemCallback<Subsection> DIFF_CALLBACK = new DiffUtil.ItemCallback<Subsection>() {
        @Override
        public boolean areItemsTheSame(@NonNull Subsection oldItem, @NonNull Subsection newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Subsection oldItem, @NonNull Subsection newItem) {
            return oldItem.equalsTo(newItem);
        }
    };
    
    public SubsectionAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_edu_entity, parent, false);
        return new EduEntityViewHolder(itemView);
    }
}
