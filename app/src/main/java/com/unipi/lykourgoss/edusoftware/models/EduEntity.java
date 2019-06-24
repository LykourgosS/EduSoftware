package com.unipi.lykourgoss.edusoftware.models;

import android.content.Context;
import android.content.Intent;

public abstract class EduEntity {

    public static String _ENTITY_REFERENCE;

    public static final String _ID = "id";
    public static final String _TITLE = "title";
    public static final String _INDEX = "index";
    public static final String _DESCRIPTION = "description";
    public static final String _CHILD_COUNT = "childCount";

    protected String id;

    protected String title;

    protected int index;

    protected String description;

    protected int childCount;

    /*Constructors (default & all properties except id)*/

    public EduEntity() {
    }

    public EduEntity(String title, int index, String description, int childCount) {
        this.title = title;
        this.index = index;
        this.description = description;
        this.childCount = childCount;
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

    public int getIndex() {
        return index;
    }

    public String getDescription() {
        return description;
    }

    public int getChildCount() {
        return childCount;
    }

    // chapters have chapterId so must override it (the same for every other class)
    public  <Model extends EduEntity> boolean equalsTo(Model model){
        //same model's properties
        return /*getId().equals(model.getId()) &&*/
                getTitle().equals(model.getTitle()) &&
                getIndex() == model.getIndex() &&
                getDescription().equals(model.getDescription())/* &&
                getChildCount() == model.getChildCount()*/;
    }

    protected abstract Intent putToIntent(Context context);
}
