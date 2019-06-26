package com.unipi.lykourgoss.edusoftware.adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.unipi.lykourgoss.edusoftware.models.Chapter;

/**
 * Created by LykourgosS <lpsarantidis@gmail.com>
 * on 25,June,2019.
 */

public class ChapterAdapter extends MyAdapter<Chapter> {

    // static because we want to be initialised before
    // executing the constructor (in which we used it)
    private static final DiffUtil.ItemCallback<Chapter> DIFF_CALLBACK = new DiffUtil.ItemCallback<Chapter>() {
        @Override
        public boolean areItemsTheSame(@NonNull Chapter oldItem, @NonNull Chapter newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Chapter oldItem, @NonNull Chapter newItem) {
            return oldItem.equalsTo(newItem);
        }
    };
    
    public ChapterAdapter() {
        super(DIFF_CALLBACK);
    }
}
