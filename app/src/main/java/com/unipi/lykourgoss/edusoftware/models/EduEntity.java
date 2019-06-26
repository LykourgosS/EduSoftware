package com.unipi.lykourgoss.edusoftware.models;

import android.content.Context;
import android.content.Intent;

public abstract class EduEntity<Model extends EduEntity> {

    public static String _ENTITY_REFERENCE;

    public static String _CLASS_NAME;

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

    /*equalsTo method:
    for comparing model's properties, although probably have
    different object reference (used for updating recyclerView adapter)*/
    abstract public boolean equalsTo(Model model);

    abstract public Intent putToIntent(Context context);

    /* method hiding: derived classes will overload getFromIntent(...) and will have
    as return type their own type */
    static public EduEntity getFromIntent(Intent intent, boolean toUpdate, int defaultIndex){
        return null;
    }
}
