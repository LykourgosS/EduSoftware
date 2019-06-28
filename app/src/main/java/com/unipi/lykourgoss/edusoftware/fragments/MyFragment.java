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
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.unipi.lykourgoss.edusoftware.Constant;
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

/**
 * Created by LykourgosS <lpsarantidis@gmail.com>
 * on 25,June,2019.
 */

public abstract class MyFragment<Model extends EduEntity, VM extends MyViewModel> extends Fragment
        implements View.OnClickListener, OnItemClickListener<Model>, Dialog.OnEditClickListener<Model> {

    protected LinearLayout linearLayoutNoItems;
    protected FloatingActionButton fabCreateNew;

    protected RecyclerView recyclerView;

    protected MyAdapter<Model> adapter;

    protected VM viewModel;

    protected CurrentViewModel currentViewModel;

    protected static boolean isEditEnabled;

    protected MyFragment(MyAdapter<Model> adapter) {
        super();
        this.adapter = adapter;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dispaly_my, container, false);

        fabCreateNew = view.findViewById(R.id.fab_create_new);

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

        currentViewModel = ViewModelProviders.of(getActivity()).get(CurrentViewModel.class);
        isEditEnabled = currentViewModel.isEditEnabled();
        if (isEditEnabled) {

            // callback for swipe to delete
            ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0,
                    ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView,
                                      @NonNull RecyclerView.ViewHolder viewHolder,
                                      @NonNull RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                    delete(adapter.getItem(viewHolder.getAdapterPosition()));
                }
            };
            final ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);

            // add swipe to delete functionality
            itemTouchHelper.attachToRecyclerView(recyclerView);
            // fab: Visible
            fabCreateNew.setOnClickListener(this);
            fabCreateNew.show();
        } else {
            fabCreateNew.hide();
        }

        // todo implement adapters click listener on sub-classes
        adapter.setOnItemClickListener(this);
    }

    /**
     * on overriding onActivityResult() check requestCode and resultCode and then create or update
     * the item and displaying corresponding Toasts
     * */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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
        menu.getItem(0).setVisible(false);
        menu.getItem(1).setVisible(isEditEnabled);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_delete_all:
                deleteAll();
                return true;
            // todo add help case: dialog for how to edit, create, delete, delete all, long click for details
        }
        return false;
    }

    /**
     * create and observe viewModel and its liveData. ViewModel object will either be newly
     * created if no ViewModel of the named class for the Activity exists, or obtained from a
     * prior instance of the Activity before a configuration change occurred.
     * */
    protected void setUpViewModel(Class<VM> vmClass, String parentId) {
        /*create and observe viewModel and its liveData. ViewModel object will either be newly
        created if no ViewModel of the named class for the Activity exists, or obtained from a
        prior instance of the Activity before a configuration change occurred.*/
        viewModel = ViewModelProviders.of(getActivity()).get(vmClass);
        viewModel.setParentId(parentId);
        viewModel.getAll().observe(getViewLifecycleOwner(), new Observer<List<Model>>() {
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

    /**
     * (1) put in Extras how many models are in viewModel (hypothesis: the new section will be
     *     added at the end of the sections -> used to select index and
     * (2) start activity for result
     * */
    protected abstract void startActivityToCreateNew();

    /**
     * (1) put in Extras the model,
     * (2) put in Extras how many models are in viewModel (hypothesis: the new section will be
     *     added at the end of the sections -> used to select index and
     * (3) start activity for result
     * */
    protected void startActivityToEdit(Model model){
        Intent intent = model.putToIntent(getActivity());
        intent.putExtra(Constant.EXTRA_LAST_INDEX, viewModel.getChildCount());
        startActivityForResult(intent, Constant.EDIT_REQUEST);
    }

    protected abstract void delete(Model model);

    protected abstract void deleteAll();

    /**
     * Open fragment displaying model's children
     * */
    @Override
    public abstract void onItemClick(Model model);

    /**
     * Show dialog with model's details (if isEditEnable an edit button will be visible)
     * */
    @Override
    public abstract void onItemLongClick(Model model);


    /**
     * Start activity to edit model (startActivityToEdit(...) is called)
     * */
    @Override
    public void onEditClick(AlertDialog dialog, Model model) {
        startActivityToEdit(model);
        dialog.cancel();
    }
}
