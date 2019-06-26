package com.unipi.lykourgoss.edusoftware.activities.createedit;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.NumberPicker;

import androidx.appcompat.widget.Toolbar;

import com.unipi.lykourgoss.edusoftware.R;
import com.unipi.lykourgoss.edusoftware.fragments.ChaptersFragment;
import com.unipi.lykourgoss.edusoftware.models.Chapter;

public class CreateEditChapterActivity extends CreateEditActivity {

    public static final String EXTRA_EXAM_QUESTION_COUNT =
            "com.unipi.lykourgoss.edusoftware.activities.createedit.CreateEditChapterActivity.EXTRA_EXAM_QUESTION_COUNT";

    private EditText editTextTitle;
    private EditText editTextDescription;
    private NumberPicker numberPickerIndex;

    private Chapter chapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_edit);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);

        editTextTitle = findViewById(R.id.edit_text_lesson_title);
        editTextDescription = findViewById(R.id.edit_text_lesson_description);
        numberPickerIndex = findViewById(R.id.number_picker_lesson_index);

        Intent intent = getIntent();

        int lastIndex = intent.getIntExtra(ChaptersFragment.EXTRA_LAST_INDEX, 1);

        numberPickerIndex.setMinValue(1);
        numberPickerIndex.setMaxValue(lastIndex);

        chapter = null;
        if (intent.hasExtra(EXTRA_ID)) { // update situation

            chapter = Chapter.getFromIntent(intent, true, 0);

            setTitle("Edit Chapter");
            // fill editTexts with lesson values for editing
            editTextTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            editTextDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            numberPickerIndex.setValue(intent.getIntExtra(EXTRA_INDEX, 1));
        } else { // create new situation
            setTitle("Create new Chapter");
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


        if (chapter != null) {
            // set every field that might have change to update
            chapter.setTitle(title);
            chapter.setDescription(description);
            chapter.setIndex(index);
            data = chapter.putToIntent();
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
