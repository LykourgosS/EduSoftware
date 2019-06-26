package com.unipi.lykourgoss.edusoftware.activities.createedit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.unipi.lykourgoss.edusoftware.R;

public class CreateEditChapterActivity extends CreateEditActivity {

    public static final String EXTRA_LESSON_ID =
            "com.unipi.lykourgoss.edusoftware.activities.createedit.CreateEditChapterActivity.EXTRA_LESSON_ID";
    public static final String EXTRA_EXAM_QUESTION_COUNT =
            "com.unipi.lykourgoss.edusoftware.activities.createedit.CreateEditChapterActivity.EXTRA_EXAM_QUESTION_COUNT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_edit_chapter);
    }
}
