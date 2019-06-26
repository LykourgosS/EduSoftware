package com.unipi.lykourgoss.edusoftware.adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.unipi.lykourgoss.edusoftware.models.Section;

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
}
