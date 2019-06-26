package com.unipi.lykourgoss.edusoftware;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.unipi.lykourgoss.edusoftware.filestoremove.codingflowexample.SwipeToDeleteCallback;
import com.unipi.lykourgoss.edusoftware.adapters.LessonAdapter;
import com.unipi.lykourgoss.edusoftware.adapters.OnItemClickListener;
import com.unipi.lykourgoss.edusoftware.activities.createedit.CreateEditLessonActivity;
import com.unipi.lykourgoss.edusoftware.models.Lesson;
import com.unipi.lykourgoss.edusoftware.viewmodels.LessonsViewModel;

import java.io.IOException;
import java.util.List;

import pl.droidsonroids.gif.GifDrawable;

public class LessonsActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int CREATE_LESSON_REQUEST = 1;
    public static final int EDIT_LESSON_REQUEST = 2;

    public static final String EXTRA_LAST_LESSON_INDEX =
            "com.unipi.lykourgoss.edusoftware.LessonsActivity.EXTRA_LAST_LESSON_INDEX";

    public AlertDialog dialog;

    private LessonsViewModel lessonsViewModel;

    private LessonAdapter adapter;

    private LinearLayout linearLayoutNoItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_dispaly_my);

        findViewById(R.id.fab_create_new).setOnClickListener(this);

        setTitle("Lessons");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        linearLayoutNoItems = findViewById(R.id.linear_layout_no_items);
        ImageView imageViewNoItems = findViewById(R.id.image_view_no_items);

        // set up imageViewNoItems with Gif drawable
        try {
            GifDrawable gifNoItems = new GifDrawable(getResources(), R.drawable.no_items_tumbleweed);
            imageViewNoItems.setImageDrawable(gifNoItems);
        } catch (IOException e) {
            Toast.makeText(LessonsActivity.this, "Error loading image", Toast.LENGTH_SHORT).show();
        }

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        adapter = new LessonAdapter();
        recyclerView.setAdapter(adapter);

        /*create and observe viewModel and its liveData. ViewModel object will either be newly
        created if no ViewModel of the named class for the Activity exists, or obtained from a
        prior instance of the Activity before a configuration change occurred.*/
        lessonsViewModel = ViewModelProviders.of(this).get(LessonsViewModel.class);
        lessonsViewModel.setParentId(null);
        lessonsViewModel.getAll().observe(this, new Observer<List<Lesson>>() {
            @Override
            public void onChanged(List<Lesson> lessons) {
                //update recyclerView items
                adapter.submitList(lessons);
                if (lessons.isEmpty()) { // if no items show tumbleweed gif
                    linearLayoutNoItems.setVisibility(View.VISIBLE);
                } else {
                    linearLayoutNoItems.setVisibility(View.GONE);
                }
            }
        });

        // todo if user is admin or better if they are on their MyLessons
        if (false) {
            new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                    ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView,
                                      @NonNull RecyclerView.ViewHolder viewHolder,
                                      @NonNull RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                    //swipe left or right to delete (<- or ->)
                    delete(adapter.getItem(viewHolder.getAdapterPosition()));
                }
            }).attachToRecyclerView(recyclerView);
        }

        SwipeToDeleteCallback<Lesson, LessonsViewModel> swipeToDeleteCallback = new SwipeToDeleteCallback<>(lessonsViewModel, adapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        adapter.setOnClickListener(new OnItemClickListener<Lesson>() {
            @Override
            public void onItemClick(Lesson lesson) {
                // todo open lesson's chapters
                Toast.makeText(LessonsActivity.this, "Start chapters activity", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(Lesson lesson) {
                /*Dialog.showLessonDetails(LessonsActivity.this, true, lesson, new Dialog.OnEditClickListener() {
                    @Override
                    public void onEditClick() {

                    }
                });*/
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        /* default value for lesson's index is the last available index */
        int defaultIndex = lessonsViewModel.getChildCount();

        if (requestCode == CREATE_LESSON_REQUEST) {
            if (resultCode == RESULT_OK) {
                lessonsViewModel.create(Lesson.getFromIntent(data, false, defaultIndex));
                Toast.makeText(this, "Lesson created", Toast.LENGTH_SHORT).show();
            } else {// something went wrong or user clicked to go back
                Toast.makeText(this, "Lesson not created", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == EDIT_LESSON_REQUEST) {
            if (resultCode == RESULT_OK) {
                lessonsViewModel.update(Lesson.getFromIntent(data, true, defaultIndex));
                Toast.makeText(this, "Lesson updated", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Lesson not updated", Toast.LENGTH_SHORT).show();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.delete_all_menu, menu);
        return true;
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

    /*Methods for CRUD operations*/

    private void startActivityToCreateNew(){
        Intent intent = new Intent(LessonsActivity.this, CreateEditLessonActivity.class);
        /*put in extras: the index for new lesson (hypothesis: will be added at the end
        of the lessons) -> used to set up numberPicker choices (for selecting index)*/
        intent.putExtra(EXTRA_LAST_LESSON_INDEX, lessonsViewModel.getChildCount() + 1);
        startActivityForResult(intent, CREATE_LESSON_REQUEST);
    }

    /*private void createNew(Lesson lesson, int resultCode) {
        if (resultCode == RESULT_OK) {
            lessonsViewModel.create(lesson);
            Toast.makeText(this, "Lesson created", Toast.LENGTH_SHORT).show();
        } else {// something went wrong or user clicked to go back
            Toast.makeText(this, "Lesson not created", Toast.LENGTH_SHORT).show();
        }
    }*/

    /* todo on call use: adapter.getItemAt(viewHolder.getAdapterPosition()) */
    private void startActivityToEdit(Lesson lesson){
        Intent intent = lesson.putToIntent(LessonsActivity.this);
        // put in extras the index of the last lesson (so the user can't set the lesson's
        // index at maximum same as the last lesson) -> used to set up numberPicker choices
        // (for selecting index)
        intent.putExtra(EXTRA_LAST_LESSON_INDEX, lessonsViewModel.getChildCount());
        startActivityForResult(intent, EDIT_LESSON_REQUEST);
    }

    private void update(Lesson lesson, int resultCode) {

    }

    private void delete(Lesson lesson){
        lessonsViewModel.delete(lesson);
        Toast.makeText(LessonsActivity.this, "Lesson deleted", Toast.LENGTH_SHORT).show();
    }

    /*deletes all lessons that don't have any children (chapters)*/
    private void deleteAll() {
        lessonsViewModel.deleteAll();
        Toast.makeText(this, "All lessons deleted", Toast.LENGTH_SHORT).show();
    }
}
