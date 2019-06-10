package com.unipi.lykourgoss.edusoftware;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;

import com.unipi.lykourgoss.edusoftware.Model.Lesson;

import java.util.ArrayList;
import java.util.List;

public class CreateLessonActivity extends AppCompatActivity {

    private EditText editTextTitle;
    private EditText editTextDescription;
    private Spinner spinnerLessonIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_lesson);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);
        setTitle("Create new Lesson");

        editTextTitle = findViewById(R.id.editText_lesson_title);
        editTextDescription = findViewById(R.id.editText_lesson_description);
        spinnerLessonIndex = findViewById(R.id.spinner_lesson_index);
        setUpSpinnerChoicesWithLessonIndexes();
    }

    private void setUpSpinnerChoicesWithLessonIndexes() {
        List<String> spinnerChoices = new ArrayList<>();
        spinnerChoices.add("Auto");

    }

    private void createNewLesson(){
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();

        if(title.trim().isEmpty()){
            editTextTitle.setError("Title is required");
            editTextTitle.requestFocus();
            return;
        }

        //create lesson object that will be stored in firebase
        Lesson lesson = new Lesson.Builder(title)
                .description(description)
                .index();
        //todo lastModifiedBy user.getEmail()
        lesson.create(CreateLessonActivity.this);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.create_edu_entity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_save_edu_entity:
                createNewLesson();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
