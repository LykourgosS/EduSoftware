package com.unipi.lykourgoss.edusoftware.activities.createedit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.NumberPicker;

import com.unipi.lykourgoss.edusoftware.R;
import com.unipi.lykourgoss.edusoftware.fragments.SectionsFragment;
import com.unipi.lykourgoss.edusoftware.models.Section;
import com.unipi.lykourgoss.edusoftware.viewmodels.CurrentViewModel;

public class CreateEditSectionActivity extends CreateEditActivity {

    private Section section;

    private EditText editTextTitle;
    private EditText editTextDescription;
    private NumberPicker numberPickerIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_edit);

        editTextTitle = findViewById(R.id.create_edit_title);
        editTextDescription = findViewById(R.id.create_edit_description);
        numberPickerIndex = findViewById(R.id.create_edit_index);

        Intent intent = getIntent();
        
        int lastIndex = intent.getIntExtra(SectionsFragment.EXTRA_LAST_INDEX, 1);

        numberPickerIndex.setMinValue(1);
        numberPickerIndex.setMaxValue(lastIndex);

        section = null;
        if (intent.hasExtra(EXTRA_ID)) { // update situation
            
            section = Section.getFromIntent(intent, true, 0);
            
            setTitle("Edit Section");
            // fill editTexts with lesson values for editing
            editTextTitle.setText(section.getTitle());
            editTextDescription.setText(section.getDescription());
            numberPickerIndex.setValue(section.getIndex());
        } else { // create new situation
            setTitle("Create New Section");
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
        
        if (section != null) {
            // set every field that might have change to update
            section.setTitle(title);
            section.setDescription(description);
            section.setIndex(index);
            data = section.putToIntent();
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
