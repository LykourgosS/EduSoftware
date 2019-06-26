package com.unipi.lykourgoss.edusoftware.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.unipi.lykourgoss.edusoftware.R;
import com.unipi.lykourgoss.edusoftware.models.EduEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LykourgosS <lpsarantidis@gmail.com>
 * on 22,June,2019.
 */

public class MyAdapter<Model extends EduEntity> extends ListAdapter<Model, MyViewHolder<Model>> {

    private OnItemClickListener listener;

    protected MyAdapter(@NonNull DiffUtil.ItemCallback<Model> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public MyViewHolder<Model> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_edu_entity, parent, false);
        return new MyViewHolder<>(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder<Model> holder, int position) {
        holder.setItem(getItem(position));
        holder.setOnItemClickListener(listener);
    }

    @Override
    public void submitList(@Nullable List<Model> list) {
        super.submitList(list != null ? new ArrayList<>(list) : null);
    }

    // todo remove following
    /*public Model getItemAt(int position) {
        return getItem(position);
    }*/

    @Override
    public Model getItem(int position) {
        return super.getItem(position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
