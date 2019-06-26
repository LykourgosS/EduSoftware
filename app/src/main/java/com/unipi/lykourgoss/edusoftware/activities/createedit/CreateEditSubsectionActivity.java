package com.unipi.lykourgoss.edusoftware.activities.createedit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.unipi.lykourgoss.edusoftware.R;

public class CreateEditSubsectionActivity extends CreateEditActivity {

    public static final String EXTRA_SECTION_ID =
            "com.unipi.lykourgoss.edusoftware.activities.createedit.CreateEditSubsectionActivity.EXTRA_SECTION_ID";
    public static final String EXTRA_PDF_URL =
            "com.unipi.lykourgoss.edusoftware.activities.createedit.CreateEditSubsectionActivity.EXTRA_PDF_URL";
    public static final String EXTRA_PDF_FILENAME =
            "com.unipi.lykourgoss.edusoftware.activities.createedit.CreateEditSubsectionActivity.EXTRA_PDF_FILENAME";
    public static final String EXTRA_TEST_QUESTION_COUNT =
            "com.unipi.lykourgoss.edusoftware.activities.createedit.CreateEditSubsectionActivity.EXTRA_TEST_QUESTION_COUNT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_edit_subsection);
    }
}
