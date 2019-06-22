/*
package com.unipi.lykourgoss.edusoftware.models;

import android.content.Context;
import android.content.Intent;

public class Chapter extends EduEntity {

    */
/*//*
/todo check if needed constant, Database child reference for Chapters
    public static final String CHAPTERS_REF = "Chapters";*//*


    */
/*Unique properties*//*

    
    private String lessonId;
    
    private int examQuestionCount;

    */
/*Constructors (default & all properties except id)*//*

    
    public Chapter() {
    }

    public Chapter(String title, int index, String description, int childrenCount, String lessonId, int examQuestionCount) {
        super(title, index, description, childrenCount);
        this.lessonId = lessonId;
        this.examQuestionCount = examQuestionCount;
    }

    */
/*Getters for this*//*

    
    public String getLessonId() {
        return lessonId;
    }

    public int getExamQuestionCount() {
        return examQuestionCount;
    }

    */
/*Override methods putToIntent()*//*


    @Override
    public Intent putToIntent(Context context, boolean toEdit) {
        Intent intent = new Intent(context, CreateEditChapterActivity.class);
        intent.putExtra(CreateEditChapterActivity.EXTRA_ID, getId());
        intent.putExtra(CreateEditChapterActivity.EXTRA_TITLE, getTitle());
        intent.putExtra(CreateEditChapterActivity.EXTRA_INDEX, getIndex());
        intent.putExtra(CreateEditChapterActivity.EXTRA_DESCRIPTION, getDescription());
        intent.putExtra(CreateEditChapterActivity.EXTRA_CHILD_COUNT, getChildrenCount());
        intent.putExtra(CreateEditChapterActivity.EXTRA_LESSON_ID, getLessonId());
        intent.putExtra(CreateEditChapterActivity.EXTRA_EXAM_QUESTION_COUNT, getExamQuestionCount());
        return intent;
    }

    */
/*getFromIntent() method returns a Chapter object taken from given Intent*//*


    public static Chapter getFromIntent(Intent intent, boolean toUpdate, int defaultIndex) {
        String title = intent.getStringExtra(CreateEditChapterActivity.EXTRA_TITLE);
        int index = intent.getIntExtra(CreateEditChapterActivity.EXTRA_INDEX, defaultIndex);
        String description = intent.getStringExtra(CreateEditChapterActivity.EXTRA_DESCRIPTION);
        int childrenCount = intent.getIntExtra(CreateEditChapterActivity.EXTRA_CHILD_COUNT, 0);
        String lessonId = intent.getStringExtra(CreateEditChapterActivity.EXTRA_LESSON_ID);
        int examQuestionCount = intent.getIntExtra(CreateEditChapterActivity.EXTRA_EXAM_QUESTION_COUNT, 0);

        Chapter chapter = new Chapter(title, index, description, childrenCount, lessonId, examQuestionCount);

        if (toUpdate){
            String id = intent.getStringExtra(CreateEditChapterActivity.EXTRA_ID);
            chapter.setId(id);
        }
        return chapter;
    }
    
    */
/*equalsTo method:
    for comparing chapter's properties, although probably have
    different object reference (used for updating recyclerView adapter)*//*


    public boolean equalsTo(Chapter otherChapter) {
        //same chapter's properties
        return super.equalsTo(otherChapter) &&
                getLessonId().equals(otherChapter.getLessonId()) && 
                getExamQuestionCount() == otherChapter.getExamQuestionCount();
    }
}
*/
