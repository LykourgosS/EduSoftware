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

import com.unipi.lykourgoss.edusoftware.adapters.LessonAdapter;
import com.unipi.lykourgoss.edusoftware.adapters.OnItemClickListener;
import com.unipi.lykourgoss.edusoftware.createeditactivities.CreateEditLessonActivity;
import com.unipi.lykourgoss.edusoftware.models.Lesson;
import com.unipi.lykourgoss.edusoftware.viewmodels.LessonViewModel;

import java.io.IOException;
import java.util.List;

import pl.droidsonroids.gif.GifDrawable;

public class LessonsActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int CREATE_LESSON_REQUEST = 1;
    public static final int EDIT_LESSON_REQUEST = 2;

    public static final String EXTRA_LAST_LESSON_INDEX =
            "com.unipi.lykourgoss.edusoftware.LessonsActivity.EXTRA_LAST_LESSON_INDEX";

    public AlertDialog dialog;

    private LessonViewModel lessonViewModel;

    private LessonAdapter adapter;

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
            gifNoItems = new GifDrawable(getResources(), R.drawable.no_items_tumbleweed);
            imageViewNoItems.setImageDrawable(gifNoItems);
        } catch (IOException e) {
            Toast.makeText(LessonsActivity.this, "Error loading image", Toast.LENGTH_SHORT).show();
        }

        recyclerView = findViewById(R.id.recycler_view_lessons);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        adapter = new LessonAdapter();
        recyclerView.setAdapter(adapter);

        /*create and observe viewModel and its liveData. ViewModel object will either be newly
        created if no ViewModel of the named class for the Activity exists, or obtained from a
        prior instance of the Activity before a configuration change occurred.*/
        lessonViewModel = ViewModelProviders.of(this).get(LessonViewModel.class);
        lessonViewModel.getAllLessons().observe(this, new Observer<List<Lesson>>() {
            @Override
            public void onChanged(List<Lesson> lessons) {
                //update recyclerView items
                adapter.submitList(lessons);
                // if no items show tumbleweed gif
                if (lessons.isEmpty()) {
                    linearLayoutNoItems.setVisibility(View.VISIBLE);
                } else {
                    linearLayoutNoItems.setVisibility(View.GONE);
                }
            }
        });

        // if user is admin or better if they are on their MyLessons
        if (true) {
            // swipe right to delete (->) or left to edit (<-)
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
                    if (direction == ItemTouchHelper.RIGHT) { //swipe right to delete (->)
                        lessonViewModel.delete(adapter.getLessonAt(viewHolder.getAdapterPosition()));
                        Toast.makeText(LessonsActivity.this, "Lesson deleted", Toast.LENGTH_SHORT).show();
                    } else { //swipe left to edit (<-)
                        // TODO should i make a method for making an intent for editing existing Lesson
                        Intent intent = adapter.getLessonAt(viewHolder.getAdapterPosition())
                                .putToIntent(LessonsActivity.this);
                        // put in extras the index of the last lesson (so the user can't set the lesson's
                        // index at maximum same as the last lesson) -> used to set up numberPicker choices
                        // (for selecting index)
                        intent.putExtra(EXTRA_LAST_LESSON_INDEX, lessonViewModel.getLessonsCount());
                        startActivityForResult(intent, EDIT_LESSON_REQUEST);
                    }
                    //todo SEE: swipe menu -> codeburst
                    adapter.notifyDataSetChanged();
                }
            }).attachToRecyclerView(recyclerView);
        }

        adapter.setOnClickListener(new OnItemClickListener<Lesson>() {
            @Override
            public void onItemClick(Lesson lesson) {
                // todo open lesson's chapters
                Toast.makeText(LessonsActivity.this, "Start chapters activity", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(Lesson lesson) {
                Dialog.showLessonDetails(LessonsActivity.this, lesson);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // default value for lesson's index is the last available index
        int defaultIndex = lessonViewModel.getLessonsCount();

        if (requestCode == CREATE_LESSON_REQUEST) {
            if (resultCode == RESULT_OK){
                // creating a new Lesson object and saving it in the database
                Lesson lesson = Lesson.getFromIntent(data, false, defaultIndex);
                lessonViewModel.insert(lesson);
                Toast.makeText(this, "Lesson created", Toast.LENGTH_SHORT).show();
            } else {// something went wrong or user clicked to go back
                Toast.makeText(this, "Lesson not created", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == EDIT_LESSON_REQUEST) {
            if (resultCode == RESULT_OK){
                /* updating existing lesson in database */
                Lesson lesson = Lesson.getFromIntent(data, true, defaultIndex);
                if (lesson != null) { // check getFromIntent(...) declaration for the purpose of if
                    lessonViewModel.update(lesson);
                    Toast.makeText(this, "Lesson updated", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            Toast.makeText(this, "Lesson not updated", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_create_lesson:
                /* Create new Lesson */
                Intent intent = new Intent(LessonsActivity.this, CreateEditLessonActivity.class);
                /*
                put in extras: the index for new lesson (hypothesis: will be added at the end
                of the lessons) -> used to set up numberPicker choices (for selecting index)
                */
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
            // todo add help case: dialog for how to edit, create, delete, delete all, long click for details
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
