package com.unipi.lykourgoss.edusoftware.activities.createedit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.unipi.lykourgoss.edusoftware.R;

public class CreateEditSectionActivity extends CreateEditActivity {

    public static final String EXTRA_CHAPTER_ID =
            "com.unipi.lykourgoss.edusoftware.activities.createedit.CreateEditSectionActivity.EXTRA_CHAPTER_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_edit_section);
    }
}
