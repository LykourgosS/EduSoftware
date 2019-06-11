package com.unipi.lykourgoss.edusoftware.Model;

import android.content.Context;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public abstract class EduEntity {

    public final static String PROP_ID = "id";
    public final static String PROP_TITLE = "title";
    public final static String PROP_DESCRIPTION = "description";
    public final static String PROP_INDEX = "index";
    public final static String PROP_LAST_MODIFIED_BY = "lastModifiedBy";

    protected final static DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();

    protected String id; //required
    protected String title; //required
    protected String description;
    protected int index; //required
    protected String lastModifiedBy; //todo required

    //public abstract int fieldsLayout();
    public abstract void create(Context context);

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }
}
