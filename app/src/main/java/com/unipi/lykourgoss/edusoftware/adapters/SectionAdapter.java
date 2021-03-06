package com.unipi.lykourgoss.edusoftware.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.unipi.lykourgoss.edusoftware.R;
import com.unipi.lykourgoss.edusoftware.models.Section;
import com.unipi.lykourgoss.edusoftware.viewholders.EduEntityViewHolder;
import com.unipi.lykourgoss.edusoftware.viewholders.MyViewHolder;

/**
 * Created by LykourgosS <lpsarantidis@gmail.com>
 * on 25,June,2019.
 */

public class SectionAdapter extends MyAdapter<Section> {

    // static because we want to be initialised before
    // executing the constructor (in which we used it)
    private static final DiffUtil.ItemCallback<Section> DIFF_CALLBACK = new DiffUtil.ItemCallback<Section>() {
        @Override
        public boolean areItemsTheSame(@NonNull Section oldItem, @NonNull Section newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Section oldItem, @NonNull Section newItem) {
            return oldItem.equalsTo(newItem);
        }
    };
    
    public SectionAdapter() {
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
