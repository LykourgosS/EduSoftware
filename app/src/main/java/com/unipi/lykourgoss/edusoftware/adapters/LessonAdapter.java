package com.unipi.lykourgoss.edusoftware.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.unipi.lykourgoss.edusoftware.R;
import com.unipi.lykourgoss.edusoftware.models.Lesson;

/**
 * Created by LykourgosS <lpsarantidis@gmail.com>
 * on 17,June,2019.
 */

public class LessonAdapter extends MyAdapter<Lesson> {

    // static because we want to be initialised before
    // executing the constructor (in which we used it)
    private static final DiffUtil.ItemCallback<Lesson> DIFF_CALLBACK = new DiffUtil.ItemCallback<Lesson>() {
        @Override
        public boolean areItemsTheSame(@NonNull Lesson oldItem, @NonNull Lesson newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Lesson oldItem, @NonNull Lesson newItem) {
            return oldItem.equalsTo(newItem);
        }
    };

    public LessonAdapter() {
        super(DIFF_CALLBACK);
    }
}
