package com.unipi.lykourgoss.edusoftware.adapters;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.unipi.lykourgoss.edusoftware.models.EduEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LykourgosS <lpsarantidis@gmail.com>
 * on 22,June,2019.
 */

public class MyAdapter<Model extends EduEntity, VH extends RecyclerView.ViewHolder> extends ListAdapter<Model, VH> {

    protected int layoutId;

    protected OnItemClickListener listener;

    protected MyAdapter(@NonNull DiffUtil.ItemCallback<Model> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {

    }

    @Override
    public void submitList(@Nullable List<Model> list) {
        super.submitList(list != null ? new ArrayList<>(list) : null);
    }

    public Model getItemAt(int position) {
        return getItem(position);
    }

    public void setOnClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
