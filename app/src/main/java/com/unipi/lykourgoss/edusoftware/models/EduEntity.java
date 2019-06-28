package com.unipi.lykourgoss.edusoftware.models;

import android.content.Context;
import android.content.Intent;

import com.unipi.lykourgoss.edusoftware.Constant;

public abstract class EduEntity<Model extends EduEntity> {

    public static String _ENTITY_REFERENCE;

    public static String _CLASS_NAME;

    public static final String _ID = "id";
    public static final String _TITLE = "title";
    public static final String _INDEX = "index";
    public static final String _DESCRIPTION = "description";
    public static final String _CHILD_COUNT = "childCount";
    public static final String _PARENT_ID = "parentId";

    protected String id;

    protected String title;

    protected int index;

    protected String description;

    protected int childCount;

    protected String parentId;

    /* Constructors (default & all properties except id) */

    public EduEntity() {
    }

    public EduEntity(String title, int index, String description, int childCount, String parentId) {
        this.title = title;
        this.index = index;
        this.description = description;
        this.childCount = childCount;
        this.parentId = parentId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getChildCount() {
        return childCount;
    }

    public void setChildCount(int childCount) {
        this.childCount = childCount;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    /*equalsTo method:
    for comparing model's properties, although probably have
    different object reference (used for updating recyclerView adapter)*/
    abstract public boolean equalsTo(Model model);

    public Intent putToIntent(){
        Intent intent = new Intent();
        intent.putExtra(Constant.EXTRA_ID, getId());
        intent.putExtra(Constant.EXTRA_TITLE, getTitle());
        intent.putExtra(Constant.EXTRA_INDEX, getIndex());
        intent.putExtra(Constant.EXTRA_DESCRIPTION, getDescription());
        intent.putExtra(Constant.EXTRA_CHILD_COUNT, getChildCount());
        intent.putExtra(Constant.EXTRA_PARENT_ID, getParentId());
        return intent;
    }

    abstract public Intent putToIntent(Context context);

    /* method hiding: derived classes will overload getFromIntent(...) and will have
    as return type their own type */
    static public EduEntity getFromIntent(Intent intent, boolean hasId, int defaultIndex){
        return null;
    }
}
