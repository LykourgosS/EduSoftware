package com.unipi.lykourgoss.edusoftware.models;

import android.content.Context;
import android.content.Intent;

import com.unipi.lykourgoss.edusoftware.Constant;
import com.unipi.lykourgoss.edusoftware.activities.createedit.CreateEditSectionActivity;

public class Section extends EduEntity<Section> {

    /* Initialization of Section Firebase Reference */

    public static final String _SECTIONS_REF = "/sections";

    public static final String _CLASS = "Section";

    static {
        _ENTITY_REFERENCE = _SECTIONS_REF;
        _CLASS_NAME = _CLASS;
    }

    /*Constructors (default & all properties except id)*/

    public Section() {
    }

    public Section(String title, int index, String description, int childCount, String parentId) {
        super(title, index, description, childCount, parentId);
    }

    /* Override methods */

    @Override
    public boolean equalsTo(Section section) {
        return getId().equals(section.getId()) &&
                getTitle().equals(section.getTitle()) &&
                getIndex() == section.getIndex() &&
                getDescription().equals(section.getDescription()) &&
                getChildCount() == section.getChildCount() &&
                getParentId().equals(section.getParentId());
    }

    @Override
    public Intent putToIntent(Context context) {
        Intent intent = new Intent(context, CreateEditSectionActivity.class);
        intent.putExtra(Constant.EXTRA_ID, getId());
        intent.putExtra(Constant.EXTRA_TITLE, getTitle());
        intent.putExtra(Constant.EXTRA_INDEX, getIndex());
        intent.putExtra(Constant.EXTRA_DESCRIPTION, getDescription());
        intent.putExtra(Constant.EXTRA_CHILD_COUNT, getChildCount());
        intent.putExtra(Constant.EXTRA_PARENT_ID, getParentId());
        return intent;
    }

    public static Section getFromIntent(Intent intent, boolean hasId, int defaultIndex) {
        String title = intent.getStringExtra(Constant.EXTRA_TITLE);
        int index = intent.getIntExtra(Constant.EXTRA_INDEX, defaultIndex);
        String description = intent.getStringExtra(Constant.EXTRA_DESCRIPTION);
        int childCount = intent.getIntExtra(Constant.EXTRA_CHILD_COUNT, 0);
        String parentId = intent.getStringExtra(Constant.EXTRA_PARENT_ID);

        Section section = new Section(title, index, description, childCount, parentId);

        if (hasId) {
            String id = intent.getStringExtra(Constant.EXTRA_ID);
            section.setId(id);
        }
        return section;
    }
}
