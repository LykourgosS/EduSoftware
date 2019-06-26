package com.unipi.lykourgoss.edusoftware.activities.createedit;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;

import androidx.appcompat.widget.Toolbar;

import com.unipi.lykourgoss.edusoftware.LessonsActivity;
import com.unipi.lykourgoss.edusoftware.R;
import com.unipi.lykourgoss.edusoftware.fragments.LessonsFragment;
import com.unipi.lykourgoss.edusoftware.fragments.MyFragment;

public class CreateEditLessonActivity extends CreateEditActivity {

    public static final String EXTRA_AUTHOR_ID =
            "com.unipi.lykourgoss.edusoftware.activities.createedit.CreateEditLessonActivity.EXTRA_AUTHOR_ID";
    public static final String EXTRA_AUTHOR_EMAIL =
            "com.unipi.lykourgoss.edusoftware.activities.createedit.CreateEditLessonActivity.EXTRA_AUTHOR_EMAIL";

    private EditText editTextTitle;
    private EditText editTextDescription;
    private NumberPicker numberPickerIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_edit_lesson);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //toolbar.setNavigationIcon(R.drawable.ic_close_white_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);

        editTextTitle = findViewById(R.id.edit_text_lesson_title);
        editTextDescription = findViewById(R.id.edit_text_lesson_description);
        numberPickerIndex = findViewById(R.id.number_picker_lesson_index);

        int lastLessonIndex = getIntent().getIntExtra(LessonsFragment.EXTRA_LAST_INDEX, 1);

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
        String id = getIntent().getStringExtra(EXTRA_ID); // using -1, because is an invalid value
        if (id != null) {
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, data);
        finish();
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
}

