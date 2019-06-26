package com.unipi.lykourgoss.edusoftware.models;

import android.content.Context;

import android.content.Intent;

import com.unipi.lykourgoss.edusoftware.activities.createedit.CreateEditChapterActivity;

public class Chapter extends EduEntity<Chapter> {

    public static final String _LESSON_ID = "lessonId";

    /* Initialization of Chapter Firebase Reference */

    public static final String _CHAPTERS_REF = "/chapters";

    public static final String _CLASS = "Chapter";

    static {
        _ENTITY_REFERENCE = _CHAPTERS_REF;
        _CLASS_NAME = _CLASS;
    }

    /*Unique properties*/

    private String lessonId;

    private int examQuestionCount;

    /*Constructors (default & all properties except id)*/

    public Chapter() {
    }

    public Chapter(String title, int index, String description, int childCount, String lessonId, int examQuestionCount) {
        super(title, index, description, childCount);
        this.lessonId = lessonId;
        this.examQuestionCount = examQuestionCount;
    }

    /*Getters for this*/

    public String getLessonId() {
        return lessonId;
    }

    public int getExamQuestionCount() {
        return examQuestionCount;
    }

    /* Override methods */

    @Override
    public boolean equalsTo(Chapter chapter) {
        return getId().equals(chapter.getId()) &&
                getTitle().equals(chapter.getTitle()) &&
                getIndex() == chapter.getIndex() &&
                getDescription().equals(chapter.getDescription()) &&
                getChildCount() == chapter.getChildCount() &&
                getLessonId().equals(chapter.getLessonId()) &&
                getExamQuestionCount() == chapter.getExamQuestionCount();
    }

    @Override
    public Intent putToIntent(Context context) {
        Intent intent = new Intent(context, CreateEditChapterActivity.class);
        intent.putExtra(CreateEditChapterActivity.EXTRA_ID, getId());
        intent.putExtra(CreateEditChapterActivity.EXTRA_TITLE, getTitle());
        intent.putExtra(CreateEditChapterActivity.EXTRA_INDEX, getIndex());
        intent.putExtra(CreateEditChapterActivity.EXTRA_DESCRIPTION, getDescription());
        intent.putExtra(CreateEditChapterActivity.EXTRA_CHILD_COUNT, getChildCount());
        intent.putExtra(CreateEditChapterActivity.EXTRA_LESSON_ID, getLessonId());
        intent.putExtra(CreateEditChapterActivity.EXTRA_EXAM_QUESTION_COUNT, getExamQuestionCount());
        return intent;
    }

    public static Chapter getFromIntent(Intent intent, boolean toUpdate, int defaultIndex) {
        String title = intent.getStringExtra(CreateEditChapterActivity.EXTRA_TITLE);
        int index = intent.getIntExtra(CreateEditChapterActivity.EXTRA_INDEX, defaultIndex);
        String description = intent.getStringExtra(CreateEditChapterActivity.EXTRA_DESCRIPTION);
        int childCount = intent.getIntExtra(CreateEditChapterActivity.EXTRA_CHILD_COUNT, 0);
        String lessonId = intent.getStringExtra(CreateEditChapterActivity.EXTRA_LESSON_ID);
        int examQuestionCount = intent.getIntExtra(CreateEditChapterActivity.EXTRA_EXAM_QUESTION_COUNT, 0);

        Chapter chapter = new Chapter(title, index, description, childCount, lessonId, examQuestionCount);

        if (toUpdate) {
            String id = intent.getStringExtra(CreateEditChapterActivity.EXTRA_ID);
            chapter.setId(id);
        }
        return chapter;
    }
}
