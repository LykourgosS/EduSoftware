package com.unipi.lykourgoss.edusoftware.models;

import android.content.Context;

import android.content.Intent;

import com.unipi.lykourgoss.edusoftware.Constant;
import com.unipi.lykourgoss.edusoftware.activities.createedit.CreateEditChapterActivity;

public class Chapter extends EduEntity<Chapter> {

    /* Initialization of Chapter Firebase Reference */

    public static final String _CHAPTERS_REF = "/chapters";

    public static final String _CLASS = "Chapter";

    static {
        _ENTITY_REFERENCE = _CHAPTERS_REF;
        _CLASS_NAME = _CLASS;
    }

    /*Unique properties*/

    private int examQuestionCount;

    /*Constructors (default & all properties except id)*/

    public Chapter() {
    }

    public Chapter(String title, int index, String description, int childCount, String parentId, int examQuestionCount) {
        super(title, index, description, childCount, parentId);
        this.examQuestionCount = examQuestionCount;
    }

    /*Getters for this*/

    public void setExamQuestionCount(int examQuestionCount) {
        this.examQuestionCount = examQuestionCount;
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
                getParentId().equals(chapter.getParentId()) &&
                getExamQuestionCount() == chapter.getExamQuestionCount();
    }

    @Override
    public Intent putToIntent() {
        Intent intent = super.putToIntent();
        intent.putExtra(Constant.EXTRA_EXAM_QUESTION_COUNT, getExamQuestionCount());
        return intent;
    }

    @Override
    public Intent putToIntent(Context context) {
        Intent intent = new Intent(context, CreateEditChapterActivity.class);
        intent.putExtra(Constant.EXTRA_ID, getId());
        intent.putExtra(Constant.EXTRA_TITLE, getTitle());
        intent.putExtra(Constant.EXTRA_INDEX, getIndex());
        intent.putExtra(Constant.EXTRA_DESCRIPTION, getDescription());
        intent.putExtra(Constant.EXTRA_CHILD_COUNT, getChildCount());
        intent.putExtra(Constant.EXTRA_PARENT_ID, getParentId());
        intent.putExtra(Constant.EXTRA_EXAM_QUESTION_COUNT, getExamQuestionCount());
        return intent;
    }

    public static Chapter getFromIntent(Intent intent, boolean hasId, int defaultIndex) {
        String title = intent.getStringExtra(Constant.EXTRA_TITLE);
        int index = intent.getIntExtra(Constant.EXTRA_INDEX, defaultIndex);
        String description = intent.getStringExtra(Constant.EXTRA_DESCRIPTION);
        int childCount = intent.getIntExtra(Constant.EXTRA_CHILD_COUNT, 0);
        String parentId = intent.getStringExtra(Constant.EXTRA_PARENT_ID);
        int examQuestionCount = intent.getIntExtra(Constant.EXTRA_EXAM_QUESTION_COUNT, 0);

        Chapter chapter = new Chapter(title, index, description, childCount, parentId, examQuestionCount);

        if (hasId) {
            String id = intent.getStringExtra(Constant.EXTRA_ID);
            chapter.setId(id);
        }
        return chapter;
    }
}
