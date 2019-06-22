/*
package com.unipi.lykourgoss.edusoftware.models;

import android.content.Context;
import android.content.Intent;

public class Section extends EduEntity {

    */
/*//*
/constant, Database child reference for Sections
    public static final String SECTIONS_REF = "Sections";*//*


    */
/*Unique properties*//*


    private String chapterId;

    */
/*Constructors (default & all properties except id)*//*


    public Section() {
    }

    public Section(String title, int index, String description, int childrenCount, String chapterId) {
        super(title, index, description, childrenCount);
        this.chapterId = chapterId;
    }

    */
/*Getters for this*//*


    public String getChapterId() {
        return chapterId;
    }

    */
/*Override methods putToIntent()*//*


    @Override
    public Intent putToIntent(Context context, boolean toEdit) {
        Intent intent = new Intent(context, CreateEditSectionActivity.class);
        intent.putExtra(CreateEditSectionActivity.EXTRA_ID, getId());
        intent.putExtra(CreateEditSectionActivity.EXTRA_TITLE, getTitle());
        intent.putExtra(CreateEditSectionActivity.EXTRA_INDEX, getIndex());
        intent.putExtra(CreateEditSectionActivity.EXTRA_DESCRIPTION, getDescription());
        intent.putExtra(CreateEditSectionActivity.EXTRA_CHILD_COUNT, getChildrenCount());
        intent.putExtra(CreateEditSectionActivity.EXTRA_CHAPTER_ID, getChapterId());
        return intent;
    }

    */
/*getFromIntent() method returns a Section object taken from given Intent*//*


    public static Section getFromIntent(Intent intent, boolean toUpdate, int defaultIndex) {
        String title = intent.getStringExtra(CreateEditSectionActivity.EXTRA_TITLE);
        int index = intent.getIntExtra(CreateEditSectionActivity.EXTRA_INDEX, defaultIndex);
        String description = intent.getStringExtra(CreateEditSectionActivity.EXTRA_DESCRIPTION);
        int childrenCount = intent.getIntExtra(CreateEditSectionActivity.EXTRA_CHILD_COUNT, 0);
        String chapterId = intent.getStringExtra(CreateEditSectionActivity.EXTRA_CHAPTER_ID);

        Section section = new Section(title, index, description, childrenCount, chapterId);

        if (toUpdate){
            String id = intent.getStringExtra(CreateEditSectionActivity.EXTRA_ID);
            section.setId(id);
        }
        return section;
    }

    */
/*equalsTo method:
    for comparing section's properties, although probably have
    different object reference (used for updating recyclerView adapter)*//*


    public boolean equalsTo(Section otherSection) {
        //same section's properties
        return super.equalsTo(otherSection) &&
                getChapterId().equals(otherSection.getChapterId());
    }
}
*/
