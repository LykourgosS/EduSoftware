package com.unipi.lykourgoss.edusoftware.models;

import android.content.Context;
import android.content.Intent;

import com.unipi.lykourgoss.edusoftware.Constant;
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

    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }

    public String getPdfUrl() {
        return pdfUrl;
    }

    public void setPdfFilename(String pdfFilename) {
        this.pdfFilename = pdfFilename;
    }

    public String getPdfFilename() {
        return pdfFilename;
    }

    public void setTestQuestionCount(int testQuestionCount) {
        this.testQuestionCount = testQuestionCount;
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
        Intent intent = super.putToIntent();
        intent.putExtra(Constant.EXTRA_PDF_URL, getPdfUrl());
        intent.putExtra(Constant.EXTRA_PDF_FILENAME, getPdfFilename());
        intent.putExtra(Constant.EXTRA_TEST_QUESTION_COUNT, getTestQuestionCount());
        return intent;
    }

    @Override
    public Intent putToIntent(Context context) {
        Intent intent = new Intent(context, CreateEditSubsectionActivity.class);
        intent.putExtra(Constant.EXTRA_ID, getId());
        intent.putExtra(Constant.EXTRA_TITLE, getTitle());
        intent.putExtra(Constant.EXTRA_INDEX, getIndex());
        intent.putExtra(Constant.EXTRA_DESCRIPTION, getDescription());
        intent.putExtra(Constant.EXTRA_CHILD_COUNT, getChildCount());
        intent.putExtra(Constant.EXTRA_PARENT_ID, getParentId());
        intent.putExtra(Constant.EXTRA_PDF_URL, getPdfUrl());
        intent.putExtra(Constant.EXTRA_PDF_FILENAME, getPdfFilename());
        intent.putExtra(Constant.EXTRA_TEST_QUESTION_COUNT, getTestQuestionCount());
        return intent;
    }

    public static Subsection getFromIntent(Intent intent, boolean hasId, int defaultIndex) {
        String title = intent.getStringExtra(Constant.EXTRA_TITLE);
        int index = intent.getIntExtra(Constant.EXTRA_INDEX, defaultIndex);
        String description = intent.getStringExtra(Constant.EXTRA_DESCRIPTION);
        int childCount = intent.getIntExtra(Constant.EXTRA_CHILD_COUNT, 0);
        String parentId = intent.getStringExtra(Constant.EXTRA_PARENT_ID);
        String pdfUrl = intent.getStringExtra(Constant.EXTRA_PDF_URL);
        String pdfFilename = intent.getStringExtra(Constant.EXTRA_PDF_FILENAME);
        int testQuestionCount = intent.getIntExtra(Constant.EXTRA_TEST_QUESTION_COUNT, 0);

        Subsection subsection = new Subsection(title, index, description, childCount, parentId, pdfUrl, pdfFilename, testQuestionCount);

        if (hasId) {
            String id = intent.getStringExtra(Constant.EXTRA_ID);
            subsection.setId(id);
        }
        return subsection;
    }
}
