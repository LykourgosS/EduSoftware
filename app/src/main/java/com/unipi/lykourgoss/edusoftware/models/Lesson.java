package com.unipi.lykourgoss.edusoftware.models;

import android.content.Context;
import android.content.Intent;

import com.unipi.lykourgoss.edusoftware.createeditactivities.CreateEditLessonActivity;

/**
 * Created by LykourgosS <lpsarantidis@gmail.com>
 * on 21,June,2019.
 */

public class Lesson extends EduEntity {

    public static final String _AUTHOR_ID ="authorId";
    public static final String _AUTHOR_EMAIL ="authorEmail";

    /*Unique properties*/

    private String authorId;

    private String authorEmail;

    /*Constructors (default & all properties except id)*/

    public Lesson() {
    }

    public Lesson(String title, int index, String description, int childCount, String authorId, String authorEmail) {
        super(title, index, description, childCount);
        this.authorId = authorId;
        this.authorEmail = authorEmail;
    }

    /*Getters for this*/

    public String getAuthorId() {
        return authorId;
    }

    public String getAuthorEmail() {
        return authorEmail;
    }

    /*Override methods putToIntent()*/

    @Override
    public Intent putToIntent(Context context) {
        Intent intent = new Intent(context, CreateEditLessonActivity.class);
        intent.putExtra(CreateEditLessonActivity.EXTRA_ID, getId());
        intent.putExtra(CreateEditLessonActivity.EXTRA_TITLE, getTitle());
        intent.putExtra(CreateEditLessonActivity.EXTRA_INDEX, getIndex());
        intent.putExtra(CreateEditLessonActivity.EXTRA_DESCRIPTION, getDescription());
        intent.putExtra(CreateEditLessonActivity.EXTRA_CHILD_COUNT, getChildCount());
        intent.putExtra(CreateEditLessonActivity.EXTRA_AUTHOR_ID, getAuthorId());
        intent.putExtra(CreateEditLessonActivity.EXTRA_AUTHOR_EMAIL, getAuthorEmail());
        return intent;
    }

    /*getFromIntent() method returns a Lesson object taken from given Intent*/

    public static Lesson getFromIntent(Intent intent, boolean toUpdate, int defaultIndex) {
        String title = intent.getStringExtra(CreateEditLessonActivity.EXTRA_TITLE);
        int index = intent.getIntExtra(CreateEditLessonActivity.EXTRA_INDEX, defaultIndex);
        String description = intent.getStringExtra(CreateEditLessonActivity.EXTRA_DESCRIPTION);
        int childCount = intent.getIntExtra(CreateEditLessonActivity.EXTRA_CHILD_COUNT, 0);
        String authorId = intent.getStringExtra(CreateEditLessonActivity.EXTRA_AUTHOR_ID);
        String authorEmail = intent.getStringExtra(CreateEditLessonActivity.EXTRA_AUTHOR_EMAIL);

        Lesson lesson = new Lesson(title, index, description, childCount, authorId, authorEmail);

        if (toUpdate){
            String id = intent.getStringExtra(CreateEditLessonActivity.EXTRA_ID);
            lesson.setId(id);
        }
        return lesson;
    }

    /*equalsTo method:
    for comparing lesson's properties, although probably have
    different object reference (used for updating recyclerView adapter)*/

    public boolean equalsTo(Lesson otherLesson) {
        //same lesson's properties
        // todo why not check all properties
        return super.equalsTo(otherLesson)/* &&
                getAuthorId().equals(otherLesson.getAuthorId()) &&
                getAuthorEmail().equals(otherLesson.getAuthorEmail())*/;
    }
}
