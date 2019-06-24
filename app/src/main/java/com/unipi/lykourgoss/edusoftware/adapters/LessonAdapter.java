package com.unipi.lykourgoss.edusoftware.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.unipi.lykourgoss.edusoftware.R;
import com.unipi.lykourgoss.edusoftware.models.Lesson;
import com.unipi.lykourgoss.edusoftware.viewholders.LessonViewHolder;

/**
 * Created by LykourgosS <lpsarantidis@gmail.com>
 * on 17,June,2019.
 */

public class LessonAdapter extends MyAdapter<Lesson, LessonViewHolder> {

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
        // todo maybe this will be the same for all entities...
        //  if we need the same fields for all of them
        this.layoutId = R.layout.item_lesson;
    }

    @NonNull
    @Override
    public LessonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(layoutId, parent, false);
        return new LessonViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LessonViewHolder holder, int position) {
        holder.setItem(getItem(position));
        holder.setOnItemClickListener(listener);
    }
}
