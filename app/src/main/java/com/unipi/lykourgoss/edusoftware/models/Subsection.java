/*
package com.unipi.lykourgoss.edusoftware.models;

import android.content.Context;
import android.content.Intent;

public class Subsection extends EduEntity{

    */
/*//*
/constant, Database child reference for Subsections
    public static final String SUBSECTIONS_REF = "Subsections";*//*



    */
/*Unique properties*//*


    private String sectionId;

    private String pdfUrl;

    private String pdfFilename;

    private int testQuestionCount;

    */
/*Constructors (default & all properties except id)*//*


    public Subsection() {
    }

    public Subsection(String title, int index, String description, int childCount, String sectionId, String pdfUrl, String pdfFilename, int testQuestionCount) {
        super(title, index, description, childCount);
        this.sectionId = sectionId;
        this.pdfUrl = pdfUrl;
        this.pdfFilename = pdfFilename;
        this.testQuestionCount = testQuestionCount;
    }

    */
/*Getters for this*//*


    public String getSectionId() {
        return sectionId;
    }

    public String getPdfUrl() {
        return pdfUrl;
    }

    public String getPdfFilename() {
        return pdfFilename;
    }

    public int getTestQuestionCount() {
        return testQuestionCount;
    }

    */
/*Override methods putToIntent() & getFromIntent()*//*


    @Override
    public Intent putToIntent(Context context, boolean toEdit) {
        Intent intent = new Intent(context, CreateEditSubsectionActivity.class);
        intent.putExtra(CreateEditSubsectionActivity.EXTRA_ID, getId());
        intent.putExtra(CreateEditSubsectionActivity.EXTRA_TITLE, getTitle());
        intent.putExtra(CreateEditSubsectionActivity.EXTRA_INDEX, getIndex());
        intent.putExtra(CreateEditSubsectionActivity.EXTRA_DESCRIPTION, getDescription());
        intent.putExtra(CreateEditSubsectionActivity.EXTRA_CHILD_COUNT, getChildCount());
        intent.putExtra(CreateEditSubsectionActivity.EXTRA_SECTION_ID, getSectionId());
        intent.putExtra(CreateEditSubsectionActivity.EXTRA_PDF_URL, getPdfUrl());
        intent.putExtra(CreateEditSubsectionActivity.EXTRA_PDF_FILENAME, getPdfFilename());
        intent.putExtra(CreateEditSubsectionActivity.EXTRA_TEST_QUESTION_COUNT, getTestQuestionCount());
        return intent;
    }

    */
/*getFromIntent() method returns a Subsection object taken from given Intent*//*


    public static Subsection getFromIntent(Intent intent, boolean toUpdate, int defaultIndex) {
        String title = intent.getStringExtra(CreateEditSubsectionActivity.EXTRA_TITLE);
        int index = intent.getIntExtra(CreateEditSubsectionActivity.EXTRA_INDEX, defaultIndex);
        String description = intent.getStringExtra(CreateEditSubsectionActivity.EXTRA_DESCRIPTION);
        int childCount = intent.getIntExtra(CreateEditSubsectionActivity.EXTRA_CHILD_COUNT, 0);
        String sectionId = intent.getStringExtra(CreateEditSubsectionActivity.EXTRA_SECTION_ID);
        String pdfUrl = intent.getStringExtra(CreateEditSubsectionActivity.EXTRA_PDF_URL);
        String pdfFilename = intent.getStringExtra(CreateEditSubsectionActivity.EXTRA_PDF_FILENAME);
        int testQuestionCount = intent.getIntExtra(CreateEditSubsectionActivity.EXTRA_TEST_QUESTION_COUNT, 0);

        Subsection subsection = new Subsection(title, index, description, childCount, sectionId, pdfUrl, pdfFilename, testQuestionCount);

        if (toUpdate){
            String id = intent.getStringExtra(CreateEditSubsectionActivity.EXTRA_ID);
            subsection.setId(id);
        }
        return subsection;
    }
    
    */
/*equalsTo method:
    for comparing subsection's properties, although probably have
    different object reference (used for updating recyclerView adapter)*//*


    public boolean equalsTo(Subsection otherSubsection) {
        //same subsection's properties
        return super.equalsTo(otherSubsection) &&
                getSectionId().equals(otherSubsection.getSectionId()) &&
                getPdfUrl().equals(otherSubsection.getPdfUrl()) &&
                getPdfFilename().equals(otherSubsection.getPdfFilename()) &&
                getTestQuestionCount() == otherSubsection.getTestQuestionCount();
    }
}
*/
