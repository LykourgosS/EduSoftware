package com.unipi.lykourgoss.edusoftware.CodingFlowExample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.NumberPicker;

import com.unipi.lykourgoss.edusoftware.R;

import java.util.ArrayList;
import java.util.List;

public class CreateEditLessonActivity extends AppCompatActivity {
    public static final String EXTRA_ID =
            "com.unipi.lykourgoss.edusoftware.CodingFlowExample.EXTRA_ID";
    public static final String EXTRA_TITLE =
            "com.unipi.lykourgoss.edusoftware.CodingFlowExample.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION =
            "com.unipi.lykourgoss.edusoftware.CodingFlowExample.EXTRA_DESCRIPTION";
    public static final String EXTRA_INDEX =
            "com.unipi.lykourgoss.edusoftware.CodingFlowExample.EXTRA_INDEX";

    private EditText editTextTitle;
    private EditText editTextDescription;
    private NumberPicker numberPickerIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_lesson);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);

        editTextTitle = findViewById(R.id.edit_text_lesson_title);
        editTextDescription = findViewById(R.id.edit_text_lesson_description);
        numberPickerIndex = findViewById(R.id.number_picker_lesson_index);

        int lastLessonIndex = getIntent().getIntExtra(Main2Activity.EXTRA_LAST_LESSON_INDEX, 1);

        numberPickerIndex.setMinValue(1);
        numberPickerIndex.setMaxValue(lastLessonIndex);

        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_ID)) { // update situation
            setTitle("Edit Lesson");
            // fill editTexts with lesson values for editing
            editTextTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            editTextDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            numberPickerIndex.setValue(intent.getIntExtra(EXTRA_INDEX, 1));
        } else { // create new situation
            setTitle("Create new Lesson");
            //default is to add it as last...
            numberPickerIndex.setValue(lastLessonIndex);
        }


    }

    private void createNewLesson() {
        String title = editTextTitle.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();
        int index = numberPickerIndex.getValue();

        if (title.isEmpty()) {
            editTextTitle.setError("Title is required");
            editTextTitle.requestFocus();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_DESCRIPTION, description);
        data.putExtra(EXTRA_INDEX, index);

        // todo string for key in firebase OR! using the index ass key
        int id = getIntent().getIntExtra(EXTRA_ID, -1); // using -1, because is an invalid value
        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, data);
        finish();

        /*//create lesson object that will be stored in firebase
        Lesson lesson = new Lesson.Builder(title)
                .description(description)
                .index()
                .build();
        //todo lastModifiedBy user.getEmail()
        lesson.create(com.unipi.lykourgoss.edusoftware.CreateEditLessonActivity.this);
        finish();*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.create_edu_entity_menu, menu);
        return true; //true or false = we either want to display or don't want to display the menu
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_save_edu_entity:
                createNewLesson();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /*private void setUpSpinnerChoicesWithIndexes() {
        List<String> spinnerChoices = new ArrayList<>();
        int lessonsCount = getIntent().getIntExtra(Main2Activity.EXTRA_LESSONS_COUNT, 0);
        for (int index = 1; index <= lessonsCount + 1; index++) {
            spinnerChoices.add(String.valueOf(index));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                spinnerChoices
        );
        //todo spinnerChoices.add("auto");
        spinnerLessonIndex.setAdapter(adapter);
    }*/
}

