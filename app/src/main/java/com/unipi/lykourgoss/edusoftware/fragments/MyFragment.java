package com.unipi.lykourgoss.edusoftware.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.unipi.lykourgoss.edusoftware.Dialog;
import com.unipi.lykourgoss.edusoftware.R;
import com.unipi.lykourgoss.edusoftware.adapters.MyAdapter;
import com.unipi.lykourgoss.edusoftware.adapters.OnItemClickListener;
import com.unipi.lykourgoss.edusoftware.models.EduEntity;
import com.unipi.lykourgoss.edusoftware.viewmodels.CurrentViewModel;
import com.unipi.lykourgoss.edusoftware.viewmodels.MyViewModel;

import java.io.IOException;
import java.util.List;

import pl.droidsonroids.gif.GifDrawable;

import static android.app.Activity.RESULT_OK;

/**
 * Created by LykourgosS <lpsarantidis@gmail.com>
 * on 25,June,2019.
 */

public abstract class MyFragment<Model extends EduEntity, VM extends MyViewModel> extends Fragment
        implements View.OnClickListener, OnItemClickListener<Model>, Dialog.OnEditClickListener<Model> {

    public static final int CREATE_NEW_REQUEST = 1;

    public static final int EDIT_REQUEST = 2;

    public static final String EXTRA_LAST_INDEX = "com.unipi.lykourgoss.edusoftware.fragments.MyFragment.EXTRA_LAST_INDEX";

    protected LinearLayout linearLayoutNoItems;
    protected FloatingActionButton fabCreateNew;

    protected RecyclerView recyclerView;

    protected MyAdapter<Model> adapter;

    protected VM viewModel;

    protected CurrentViewModel currentViewModel;

    protected static boolean isEditEnabled;

    public MyFragment(MyAdapter<Model> adapter) {
        super();
        this.adapter = adapter;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dispaly_my, container, false);

        fabCreateNew = view.findViewById(R.id.fab_create_new);
        fabCreateNew.setOnClickListener(this);
        fabCreateNew.hide();

        // set up imageViewNoItems with Gif drawable
        linearLayoutNoItems = view.findViewById(R.id.linear_layout_no_items);
        ImageView imageViewNoItems = view.findViewById(R.id.image_view_no_items);
        try {
            GifDrawable gifNoItems = new GifDrawable(getResources(), R.drawable.no_items_tumbleweed);
            imageViewNoItems.setImageDrawable(gifNoItems);
        } catch (IOException e) {
            Toast.makeText(getActivity(), "Error loading image", Toast.LENGTH_SHORT).show();
        }

        // set up recyclerView
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        return view;
    }

    // todo override onActivityCreated and execute setUpViewModel
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // callback for swipe to delete
        ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                delete(adapter.getItem(viewHolder.getAdapterPosition()));
            }
        };
        final ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);

        currentViewModel = ViewModelProviders.of(getActivity()).get(CurrentViewModel.class);
        isEditEnabled = currentViewModel.isEditEnabled();
        if (isEditEnabled) {
            // add swipe to delete functionality
            itemTouchHelper.attachToRecyclerView(recyclerView);
            // fab: Visible
            fabCreateNew.show();
        } else {
            itemTouchHelper.attachToRecyclerView(null);
            fabCreateNew.hide();
        }

        // todo implement adapters click listener on sub-classes
        adapter.setOnClickListener(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        /* default value for model's index is the last available index */
        int defaultIndex = viewModel.getChildCount();

        if (requestCode == CREATE_NEW_REQUEST) {
            if (resultCode == RESULT_OK) {
                // todo viewModel.create(Model.getFromIntent(data, false, defaultIndex));
                createNew(data, defaultIndex);
                Toast.makeText(getActivity(), Model._CLASS_NAME + " created", Toast.LENGTH_SHORT).show();
            } else {// something went wrong or user clicked to go back
                Toast.makeText(getActivity(), Model._CLASS_NAME + " not created", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == EDIT_REQUEST) {
            if (resultCode == RESULT_OK) {
                // todo viewModel.update(Model.getFromIntent(data, true, defaultIndex));
                update(data, defaultIndex);
                Toast.makeText(getActivity(), Model._CLASS_NAME + " updated", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), Model._CLASS_NAME + " not updated", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_create_new:
                startActivityToCreateNew();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuInflater menuInflater = getActivity().getMenuInflater();
        menuInflater.inflate(R.menu.delete_all_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_delete_all:
                deleteAll();
                return true;
            // todo add help case: dialog for how to edit, create, delete, delete all, long click for details
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPrepareOptionsMenu(final Menu menu) {
        menu.findItem(R.id.menu_item_delete_all).setEnabled(isEditEnabled);
    }

    // for displaying all (not only my) pass parentId: null
    protected void setUpViewModel(Class<VM> vmClass, String parentId) {
        /*create and observe viewModel and its liveData. ViewModel object will either be newly
        created if no ViewModel of the named class for the Activity exists, or obtained from a
        prior instance of the Activity before a configuration change occurred.*/
        viewModel = ViewModelProviders.of(this).get(vmClass);
        viewModel.setParentId(parentId);
        viewModel.getAll().observe(this, new Observer<List<Model>>() {
            @Override
            public void onChanged(List<Model> models) {
                //update recyclerView items
                adapter.submitList(models);
                if (models.isEmpty()) { // if no items show tumbleweed gif
                    linearLayoutNoItems.setVisibility(View.VISIBLE);
                } else {
                    linearLayoutNoItems.setVisibility(View.GONE);
                }
            }
        });
    }

    protected abstract void startActivityToCreateNew();

    protected abstract void startActivityToEdit(Model model);

    protected void delete(Model model) {
        viewModel.delete(model);
    }

    protected abstract void createNew(Intent data, int resultCode);

    protected abstract void update(Intent data, int resultCode);

    protected void deleteAll() {
        viewModel.deleteAll();
        Toast.makeText(getActivity(), "All " + Model._CLASS_NAME + "s deleted", Toast.LENGTH_SHORT).show();
    }
}
