package com.unipi.lykourgoss.edusoftware.models;

import android.content.Context;
import android.content.Intent;

import com.unipi.lykourgoss.edusoftware.activities.createedit.CreateEditSectionActivity;

public class Section extends EduEntity<Section> {

    public static final String _CHAPTER_ID = "chapterId";

    /* Initialization of Section Firebase Reference */

    public static final String _SECTIONS_REF = "/sections";

    public static final String _CLASS = "Section";

    static {
        _ENTITY_REFERENCE = _SECTIONS_REF;
        _CLASS_NAME = _CLASS;
    }

    /*Unique properties*/

    private String chapterId;

    /*Constructors (default & all properties except id)*/

    public Section() {
    }

    public Section(String title, int index, String description, int childCount, String chapterId) {
        super(title, index, description, childCount);
        this.chapterId = chapterId;
    }

    /*Getters for this*/

    public String getChapterId() {
        return chapterId;
    }

    /* Override methods */

    @Override
    public boolean equalsTo(Section section) {
        return getId().equals(section.getId()) &&
                getTitle().equals(section.getTitle()) &&
                getIndex() == section.getIndex() &&
                getDescription().equals(section.getDescription()) &&
                getChildCount() == section.getChildCount() &&
                getChapterId().equals(section.getChapterId());
    }

    @Override
    public Intent putToIntent(Context context) {
        Intent intent = new Intent(context, CreateEditSectionActivity.class);
        intent.putExtra(CreateEditSectionActivity.EXTRA_ID, getId());
        intent.putExtra(CreateEditSectionActivity.EXTRA_TITLE, getTitle());
        intent.putExtra(CreateEditSectionActivity.EXTRA_INDEX, getIndex());
        intent.putExtra(CreateEditSectionActivity.EXTRA_DESCRIPTION, getDescription());
        intent.putExtra(CreateEditSectionActivity.EXTRA_CHILD_COUNT, getChildCount());
        intent.putExtra(CreateEditSectionActivity.EXTRA_CHAPTER_ID, getChapterId());
        return intent;
    }

    public static Section getFromIntent(Intent intent, boolean toUpdate, int defaultIndex) {
        String title = intent.getStringExtra(CreateEditSectionActivity.EXTRA_TITLE);
        int index = intent.getIntExtra(CreateEditSectionActivity.EXTRA_INDEX, defaultIndex);
        String description = intent.getStringExtra(CreateEditSectionActivity.EXTRA_DESCRIPTION);
        int childCount = intent.getIntExtra(CreateEditSectionActivity.EXTRA_CHILD_COUNT, 0);
        String chapterId = intent.getStringExtra(CreateEditSectionActivity.EXTRA_CHAPTER_ID);

        Section section = new Section(title, index, description, childCount, chapterId);

        if (toUpdate) {
            String id = intent.getStringExtra(CreateEditSectionActivity.EXTRA_ID);
            section.setId(id);
        }
        return section;
    }
}
