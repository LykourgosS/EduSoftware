package com.unipi.lykourgoss.edusoftware.activities.createedit;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.NumberPicker;

import com.unipi.lykourgoss.edusoftware.R;
import com.unipi.lykourgoss.edusoftware.fragments.LessonsFragment;
import com.unipi.lykourgoss.edusoftware.models.Lesson;

public class CreateEditLessonActivity extends CreateEditActivity {

    public static final String EXTRA_AUTHOR_EMAIL =
            "com.unipi.lykourgoss.edusoftware.activities.createedit.CreateEditLessonActivity.EXTRA_AUTHOR_EMAIL";

    private EditText editTextTitle;
    private EditText editTextDescription;
    private NumberPicker numberPickerIndex;

    private Lesson lesson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_edit);

        editTextTitle = findViewById(R.id.create_edit_title);
        editTextDescription = findViewById(R.id.create_edit_description);
        numberPickerIndex = findViewById(R.id.create_edit_index);

        Intent intent = getIntent();

        int lastIndex = intent.getIntExtra(LessonsFragment.EXTRA_LAST_INDEX, 1);

        numberPickerIndex.setMinValue(1);
        numberPickerIndex.setMaxValue(lastIndex);

        lesson = null;
        if (intent.hasExtra(EXTRA_ID)) { // update situation

            lesson = Lesson.getFromIntent(intent, true, 0);

            setTitle("Edit Lesson");
            // fill editTexts with lesson values for editing
            editTextTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            editTextDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            numberPickerIndex.setValue(intent.getIntExtra(EXTRA_INDEX, 1));
        } else { // create new situation
            setTitle("Create Νew Lesson");
            //default is to add it as last...
            numberPickerIndex.setValue(lastIndex);
        }
    }

    @Override
    protected void saveEntity() {
        String title = editTextTitle.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();
        int index = numberPickerIndex.getValue();

        if (title.isEmpty()) {
            editTextTitle.setError("Title is required");
            editTextTitle.requestFocus();
            return;
        }

        Intent data;

        if (lesson != null) {
            // set every field that might have change to update
            lesson.setTitle(title);
            lesson.setDescription(description);
            lesson.setIndex(index);
            data = lesson.putToIntent();
        } else {
            // put extras for creating new
            data = new Intent();
            data.putExtra(EXTRA_TITLE, title);
            data.putExtra(EXTRA_DESCRIPTION, description);
            data.putExtra(EXTRA_INDEX, index);
        }

        setResult(RESULT_OK, data);
        finish();
    }
}

