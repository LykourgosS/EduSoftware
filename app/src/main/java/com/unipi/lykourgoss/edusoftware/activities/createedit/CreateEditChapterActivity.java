package com.unipi.lykourgoss.edusoftware.activities.createedit;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.NumberPicker;

import com.unipi.lykourgoss.edusoftware.Constant;
import com.unipi.lykourgoss.edusoftware.R;
import com.unipi.lykourgoss.edusoftware.models.Chapter;

public class CreateEditChapterActivity extends CreateEditActivity {

    private EditText editTextTitle;
    private EditText editTextDescription;
    private NumberPicker numberPickerIndex;

    private Chapter chapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_edit);

        editTextTitle = findViewById(R.id.create_edit_title);
        editTextDescription = findViewById(R.id.create_edit_description);
        numberPickerIndex = findViewById(R.id.create_edit_index);

        Intent intent = getIntent();

        int lastIndex = intent.getIntExtra(Constant.EXTRA_LAST_INDEX, 1);

        numberPickerIndex.setMinValue(1);
        numberPickerIndex.setMaxValue(lastIndex);

        chapter = null;
        if (intent.hasExtra(Constant.EXTRA_ID)) { // update situation

            chapter = Chapter.getFromIntent(intent, true, 0);

            setTitle("Edit Chapter");
            // fill editTexts with lesson values for editing
            editTextTitle.setText(intent.getStringExtra(Constant.EXTRA_TITLE));
            editTextDescription.setText(intent.getStringExtra(Constant.EXTRA_DESCRIPTION));
            numberPickerIndex.setValue(intent.getIntExtra(Constant.EXTRA_INDEX, 1));
        } else { // create new situation
            setTitle("Create Νew Chapter");
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
            data.putExtra(Constant.EXTRA_TITLE, title);
            data.putExtra(Constant.EXTRA_DESCRIPTION, description);
            data.putExtra(Constant.EXTRA_INDEX, index);
        }

        setResult(RESULT_OK, data);
        finish();
    }
}
