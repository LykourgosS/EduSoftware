package com.unipi.lykourgoss.edusoftware.models;

import android.content.Context;
import android.content.Intent;

import com.unipi.lykourgoss.edusoftware.activities.createedit.CreateEditSubsectionActivity;

public class Subsection extends EduEntity<Subsection> {

    public static final String _PDF_URL = "pdfUrl";

    public static final String _PDF_FILENAME = "pdfFilename";

    public static final String _TEST_QUESTION_COUNT = "testQuestionCount";

    /* Initialization of Subsection Firebase Reference */

    public static final String _SUBSECTIONS_REF = "/subsections";

    public static final String _CLASS = "Subsection";

    static {
        _ENTITY_REFERENCE = _SUBSECTIONS_REF;
        _CLASS_NAME = _CLASS;
    }

    /* Unique properties */

    private String pdfUrl;

    private String pdfFilename;

    private int testQuestionCount;

    /* Constructors (default & all properties except id) */

    public Subsection() {
    }

    public Subsection(String title, int index, String description, int childCount, String parentId, String pdfUrl, String pdfFilename, int testQuestionCount) {
        super(title, index, description, childCount, parentId);
        this.pdfUrl = pdfUrl;
        this.pdfFilename = pdfFilename;
        this.testQuestionCount = testQuestionCount;
    }

    /* Getters for this */

    public String getPdfUrl() {
        return pdfUrl;
    }

    public String getPdfFilename() {
        return pdfFilename;
    }

    public int getTestQuestionCount() {
        return testQuestionCount;
    }

    /* Override methods */

    @Override
    public boolean equalsTo(Subsection subsection) {
        return getId().equals(subsection.getId()) &&
                getTitle().equals(subsection.getTitle()) &&
                getIndex() == subsection.getIndex() &&
                getDescription().equals(subsection.getDescription()) &&
                getChildCount() == subsection.getChildCount() &&
                getParentId().equals(subsection.getParentId()) &&
                getPdfUrl().equals(subsection.getPdfUrl()) &&
                getPdfFilename().equals(subsection.getPdfFilename()) &&
                getTestQuestionCount() == subsection.getTestQuestionCount();
    }

    @Override
    public Intent putToIntent() {
        Intent intent = new Intent();
        intent.putExtra(CreateEditSubsectionActivity.EXTRA_ID, getId());
        intent.putExtra(CreateEditSubsectionActivity.EXTRA_TITLE, getTitle());
        intent.putExtra(CreateEditSubsectionActivity.EXTRA_INDEX, getIndex());
        intent.putExtra(CreateEditSubsectionActivity.EXTRA_DESCRIPTION, getDescription());
        intent.putExtra(CreateEditSubsectionActivity.EXTRA_CHILD_COUNT, getChildCount());
        intent.putExtra(CreateEditSubsectionActivity.EXTRA_PARENT_ID, getParentId());
        intent.putExtra(CreateEditSubsectionActivity.EXTRA_PDF_URL, getPdfUrl());
        intent.putExtra(CreateEditSubsectionActivity.EXTRA_PDF_FILENAME, getPdfFilename());
        intent.putExtra(CreateEditSubsectionActivity.EXTRA_TEST_QUESTION_COUNT, getTestQuestionCount());
        return intent;
    }

    @Override
    public Intent putToIntent(Context context) {
        Intent intent = new Intent(context, CreateEditSubsectionActivity.class);
        intent.putExtra(CreateEditSubsectionActivity.EXTRA_ID, getId());
        intent.putExtra(CreateEditSubsectionActivity.EXTRA_TITLE, getTitle());
        intent.putExtra(CreateEditSubsectionActivity.EXTRA_INDEX, getIndex());
        intent.putExtra(CreateEditSubsectionActivity.EXTRA_DESCRIPTION, getDescription());
        intent.putExtra(CreateEditSubsectionActivity.EXTRA_CHILD_COUNT, getChildCount());
        intent.putExtra(CreateEditSubsectionActivity.EXTRA_PARENT_ID, getParentId());
        intent.putExtra(CreateEditSubsectionActivity.EXTRA_PDF_URL, getPdfUrl());
        intent.putExtra(CreateEditSubsectionActivity.EXTRA_PDF_FILENAME, getPdfFilename());
        intent.putExtra(CreateEditSubsectionActivity.EXTRA_TEST_QUESTION_COUNT, getTestQuestionCount());
        return intent;
    }

    public static Subsection getFromIntent(Intent intent, boolean toUpdate, int defaultIndex) {
        String title = intent.getStringExtra(CreateEditSubsectionActivity.EXTRA_TITLE);
        int index = intent.getIntExtra(CreateEditSubsectionActivity.EXTRA_INDEX, defaultIndex);
        String description = intent.getStringExtra(CreateEditSubsectionActivity.EXTRA_DESCRIPTION);
        int childCount = intent.getIntExtra(CreateEditSubsectionActivity.EXTRA_CHILD_COUNT, 0);
        String parentId = intent.getStringExtra(CreateEditSubsectionActivity.EXTRA_PARENT_ID);
        String pdfUrl = intent.getStringExtra(CreateEditSubsectionActivity.EXTRA_PDF_URL);
        String pdfFilename = intent.getStringExtra(CreateEditSubsectionActivity.EXTRA_PDF_FILENAME);
        int testQuestionCount = intent.getIntExtra(CreateEditSubsectionActivity.EXTRA_TEST_QUESTION_COUNT, 0);

        Subsection subsection = new Subsection(title, index, description, childCount, parentId, pdfUrl, pdfFilename, testQuestionCount);

        if (toUpdate) {
            String id = intent.getStringExtra(CreateEditSubsectionActivity.EXTRA_ID);
            subsection.setId(id);
        }
        return subsection;
    }
}
