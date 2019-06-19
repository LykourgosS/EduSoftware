package com.unipi.lykourgoss.edusoftware.CodingFlowExample;

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
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.unipi.lykourgoss.edusoftware.R;

import java.io.IOException;
import java.util.List;

import pl.droidsonroids.gif.GifDrawable;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {

    public static final int CREATE_LESSON_REQUEST = 1;
    public static final int EDIT_LESSON_REQUEST = 2;

    public static final String EXTRA_LAST_LESSON_INDEX =
            "com.unipi.lykourgoss.edusoftware.CodingFlowExample.EXTRA_LAST_LESSON_INDEX";

    private LessonViewModel lessonViewModel;

    private RecyclerView recyclerView;
    private ImageView imageViewNoItems;
    private GifDrawable gifNoItems;
    private LinearLayout linearLayoutNoItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        findViewById(R.id.fab_create_lesson).setOnClickListener(this);

        setTitle("Lessons");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        linearLayoutNoItems = findViewById(R.id.linear_layout_no_items);
        imageViewNoItems = findViewById(R.id.image_view_no_items);

        // set up imageViewNoItems with Gif drawable
        try {
            gifNoItems = new GifDrawable( getResources(), R.drawable.no_items_tumbleweed);
            imageViewNoItems.setImageDrawable(gifNoItems);
        } catch (IOException e) {
            Toast.makeText(Main2Activity.this, "Error loading image", Toast.LENGTH_SHORT).show();
        }

        recyclerView = findViewById(R.id.recycler_view_lessons);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final LessonAdapter adapter = new LessonAdapter(this);
        recyclerView.setAdapter(adapter);

        // create and observe viewModel and its liveData
        lessonViewModel = ViewModelProviders.of(this).get(LessonViewModel.class);
        lessonViewModel.getAllLessons().observe(this, new Observer<List<Lesson>>() {
            @Override
            public void onChanged(List<Lesson> lessons) {
                //update recyclerView
                adapter.setLessons(lessons);
                // show lessons or no items gif
                setUpVisibilities(lessons.size() == 0);
            }
        });

        // swipe right to delete (->) or left to edit (<-)
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                if (direction == ItemTouchHelper.RIGHT) { //swipe right to delete (->)
                    lessonViewModel.delete(adapter.getLessonAt(viewHolder.getAdapterPosition()));
                    Toast.makeText(Main2Activity.this, "Lesson deleted", Toast.LENGTH_SHORT).show();
                } else { //swipe left to edit (<-)
                    //todo edit functionality here
                    adapter.notifyDataSetChanged();
                    Toast.makeText(Main2Activity.this, "Lesson to edit", Toast.LENGTH_SHORT).show();
                }
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new LessonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Lesson lesson) {
                // TODO method for making an intent for editing existing Lesson
                Intent intent = new Intent(Main2Activity.this, CreateEditLessonActivity.class);
                intent.putExtra(CreateEditLessonActivity.EXTRA_ID, lesson.getId());
                intent.putExtra(CreateEditLessonActivity.EXTRA_TITLE, lesson.getTitle());
                intent.putExtra(CreateEditLessonActivity.EXTRA_INDEX, lesson.getIndex());
                intent.putExtra(CreateEditLessonActivity.EXTRA_DESCRIPTION, lesson.getDescription());
                // put in extras: the index of the last lesson (so the user can't set the lesson's
                // index at maximum same as the last lesson) -> used to set up numberPicker choices
                // (for selecting index)
                intent.putExtra(EXTRA_LAST_LESSON_INDEX, lessonViewModel.getLessonsCount());
                startActivityForResult(intent, EDIT_LESSON_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CREATE_LESSON_REQUEST && resultCode == RESULT_OK) {
            // creating a new Lesson object and saving it in the database
            String title = data.getStringExtra(CreateEditLessonActivity.EXTRA_TITLE);
            String description = data.getStringExtra(CreateEditLessonActivity.EXTRA_DESCRIPTION);
            int index = data.getIntExtra(CreateEditLessonActivity.EXTRA_INDEX,
                    lessonViewModel.getAllLessons().getValue().size()); //means add it as the last lesson

            Lesson lesson = new Lesson(title, description, index, ""/*todo getUserEmail*/);
            lessonViewModel.insert(lesson);

            Toast.makeText(this, "Lesson created", Toast.LENGTH_SHORT).show();

        } else if (requestCode == EDIT_LESSON_REQUEST && resultCode == RESULT_OK) {
            // todo update lesson to database

            int id = data.getIntExtra(CreateEditLessonActivity.EXTRA_ID, -1);
            // check if id = -1 => something went really wrong
            if (id == -1) {
                Toast.makeText(this, "Lesson cannot be updated", Toast.LENGTH_SHORT).show();
                return;
            }

            String title = data.getStringExtra(CreateEditLessonActivity.EXTRA_TITLE);
            String description = data.getStringExtra(CreateEditLessonActivity.EXTRA_DESCRIPTION);
            int index = data.getIntExtra(CreateEditLessonActivity.EXTRA_INDEX,
                    lessonViewModel.getAllLessons().getValue().size()); //means add it as the last lesson

            Lesson lesson = new Lesson(title, description, index, ""/*todo getUserEmail*/);
            lesson.setId(id);
            lessonViewModel.update(lesson);

            Toast.makeText(this, "Lesson updated", Toast.LENGTH_SHORT).show();

        } else { // something went wrong or user clicked to go back
            Toast.makeText(this, "Lesson not created", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_create_lesson:
                // Create new Lesson
                Intent intent = new Intent(Main2Activity.this, CreateEditLessonActivity.class);
                // put in extras: the index for new lesson (hypothesis: will be added at the end
                // of the lessons) -> used to set up numberPicker choices (for selecting index)
                intent.putExtra(EXTRA_LAST_LESSON_INDEX, lessonViewModel.getLessonsCount() + 1);
                startActivityForResult(intent, CREATE_LESSON_REQUEST);
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
            case R.id.item_delete_all:
                // Delete all lessons
                lessonViewModel.deleteAllLessons();
                Toast.makeText(this, "All lessons deleted", Toast.LENGTH_SHORT).show();
                return true;
            // todo add help case: dialog for how to edit, create, delete, delete all, click for description
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setUpVisibilities(boolean noItems){
        if (noItems) {
//            recyclerView.setVisibility(View.GONE);
            linearLayoutNoItems.setVisibility(View.VISIBLE);
        } else {
//            recyclerView.setVisibility(View.VISIBLE);
            linearLayoutNoItems.setVisibility(View.GONE);
        }
    }
}
