package com.unipi.lykourgoss.edusoftware.adapters;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.unipi.lykourgoss.edusoftware.viewholders.MyViewHolder;
import com.unipi.lykourgoss.edusoftware.viewholders.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LykourgosS <lpsarantidis@gmail.com>
 * on 22,June,2019.
 */

public abstract class MyAdapter<T> extends ListAdapter<T, MyViewHolder> {

    private OnItemClickListener listener;

    protected MyAdapter(@NonNull DiffUtil.ItemCallback<T> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public abstract MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.setItem(getItem(position));
        holder.setOnItemClickListener(listener);
    }

    @Override
    public void submitList(@Nullable List<T> list) {
        super.submitList(list != null ? new ArrayList<>(list) : null);
    }

    @Override
    public T getItem(int position) {
        return super.getItem(position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
