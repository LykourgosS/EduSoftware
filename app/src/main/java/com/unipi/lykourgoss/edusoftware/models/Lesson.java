package com.unipi.lykourgoss.edusoftware.models;

import android.content.Context;
import android.content.Intent;

import com.unipi.lykourgoss.edusoftware.activities.createedit.CreateEditLessonActivity;

/**
 * Created by LykourgosS <lpsarantidis@gmail.com>
 * on 21,June,2019.
 */

public class Lesson extends EduEntity<Lesson>{

    public static final String _AUTHOR_EMAIL ="authorEmail";

    /* Initialization of Lesson Firebase Reference */

    public static final String _LESSONS_REF = "lessons";

    public static final String _CLASS = "Lesson";

    static {
        _ENTITY_REFERENCE = _LESSONS_REF;
        _CLASS_NAME = _CLASS;
    }

    /* Unique properties */

    private String authorEmail;

    /* Constructors (default & all properties except id) */

    public Lesson() {
    }

    public Lesson(String title, int index, String description, int childCount, String parentId, String authorEmail) {
        super(title, index, description, childCount, parentId);
        this.authorEmail = authorEmail;
    }

    /* Getters for this */

    public void setAuthorEmail(String authorEmail) {
        this.authorEmail = authorEmail;
    }

    public String getAuthorEmail() {
        return authorEmail;
    }

    /* Override methods */

    @Override
    public boolean equalsTo(Lesson lesson) {
        return /*getId().equals(lesson.getId()) &&*/
                getTitle().equals(lesson.getTitle()) &&
                getIndex() == lesson.getIndex() &&
                getDescription().equals(lesson.getDescription())/* &&
                getChildCount() == lesson.getChildCount() &&
                getParentId().equals(lesson.getParentId()) &&
                getAuthorEmail().equals(lesson.getAuthorEmail())*/;
    }

    @Override
    public Intent putToIntent() {
        Intent intent = new Intent();
        intent.putExtra(CreateEditLessonActivity.EXTRA_ID, getId());
        intent.putExtra(CreateEditLessonActivity.EXTRA_TITLE, getTitle());
        intent.putExtra(CreateEditLessonActivity.EXTRA_INDEX, getIndex());
        intent.putExtra(CreateEditLessonActivity.EXTRA_DESCRIPTION, getDescription());
        intent.putExtra(CreateEditLessonActivity.EXTRA_CHILD_COUNT, getChildCount());
        intent.putExtra(CreateEditLessonActivity.EXTRA_PARENT_ID, getParentId());
        intent.putExtra(CreateEditLessonActivity.EXTRA_AUTHOR_EMAIL, getAuthorEmail());
        return intent;
    }

    @Override
    public Intent putToIntent(Context context) {
        Intent intent = new Intent(context, CreateEditLessonActivity.class);
        intent.putExtra(CreateEditLessonActivity.EXTRA_ID, getId());
        intent.putExtra(CreateEditLessonActivity.EXTRA_TITLE, getTitle());
        intent.putExtra(CreateEditLessonActivity.EXTRA_INDEX, getIndex());
        intent.putExtra(CreateEditLessonActivity.EXTRA_DESCRIPTION, getDescription());
        intent.putExtra(CreateEditLessonActivity.EXTRA_CHILD_COUNT, getChildCount());
        intent.putExtra(CreateEditLessonActivity.EXTRA_PARENT_ID, getParentId());
        intent.putExtra(CreateEditLessonActivity.EXTRA_AUTHOR_EMAIL, getAuthorEmail());
        return intent;
    }

    public static Lesson getFromIntent(Intent intent, boolean toUpdate, int defaultIndex) {
        String title = intent.getStringExtra(CreateEditLessonActivity.EXTRA_TITLE);
        int index = intent.getIntExtra(CreateEditLessonActivity.EXTRA_INDEX, defaultIndex);
        String description = intent.getStringExtra(CreateEditLessonActivity.EXTRA_DESCRIPTION);
        int childCount = intent.getIntExtra(CreateEditLessonActivity.EXTRA_CHILD_COUNT, 0);
        String authorId = intent.getStringExtra(CreateEditLessonActivity.EXTRA_PARENT_ID);
        String authorEmail = intent.getStringExtra(CreateEditLessonActivity.EXTRA_AUTHOR_EMAIL);

        Lesson lesson = new Lesson(title, index, description, childCount, authorId, authorEmail);

        if (toUpdate){
            String id = intent.getStringExtra(CreateEditLessonActivity.EXTRA_ID);
            lesson.setId(id);
        }
        return lesson;
    }
}
