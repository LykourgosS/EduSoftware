package com.unipi.lykourgoss.edusoftware.adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.unipi.lykourgoss.edusoftware.models.EduEntity;
import com.unipi.lykourgoss.edusoftware.viewmodels.MyViewModel;

/**
 * Created by LykourgosS <lpsarantidis@gmail.com>
 * on 22,June,2019.
 */

public class SwipeToDelete<Model extends EduEntity, VM extends MyViewModel> extends ItemTouchHelper.SimpleCallback {

    private VM viewModel;

    private MyAdapter<Model, RecyclerView.ViewHolder> adapter;

    public SwipeToDelete(VM viewModel, MyAdapter adapter) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.viewModel = viewModel;
        this.adapter = adapter;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        viewModel.delete(adapter.getItemAt(viewHolder.getAdapterPosition()));
    }
}
