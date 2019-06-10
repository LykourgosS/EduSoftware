package com.unipi.lykourgoss.edusoftware.Model;

import android.content.Context;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public abstract class EduEntity {


    protected final static DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();

    protected String id;
    protected String title;
    protected String description;
    protected int index;
    protected String lastModifiedBy;

//    public abstract int fieldsLayout();
    public abstract void create(Context context);
}
