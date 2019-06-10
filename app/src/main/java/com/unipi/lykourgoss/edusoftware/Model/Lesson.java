package com.unipi.lykourgoss.edusoftware.Model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.unipi.lykourgoss.edusoftware.Dialog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Lesson extends EduEntity {

    //constant, Database child reference for Lessons
    private static final String LESSONS_REF = "Lessons";
    public static int lessonsCount;
//    public static final int LESSONS_FIELDS_LAYOUT = R.layout.fields_lesson;

    public Lesson() {
    }

//    @Override
//    public int fieldsLayout() {
//        return LESSONS_FIELDS_LAYOUT;
//    }

    @Override
    public void create(final Context context) {
        Dialog.progressbarAction(context, Dialog.CREATING);
        Map<String, Object> childUpdates = new HashMap<>();

        //stores this lesson object to firebase (/LESSONS_REF/id)
        childUpdates.put("/" + LESSONS_REF + "/" + id, this);
        dbRef.updateChildren(childUpdates)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Dialog.dismiss();
                        Toast.makeText(context, "Lesson created", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Dialog.dismiss();
                        Toast.makeText(context, "Error creating Lesson", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private static void attachLessonsCountListener(){
        dbRef.child(LESSONS_REF).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                lessonsCount = (int) dataSnapshot.getChildrenCount();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                lessonsCount = (int) dataSnapshot.getChildrenCount();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public Lesson(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.index = builder.index;
        this.lastModifiedBy = builder.lastModifiedBy;
    }

    public static class Builder{
        private final String id;
        private String title;
        private String description;
        private int index;
        private String lastModifiedBy;

        public Builder(String title) {
            this.id = dbRef.child(LESSONS_REF).push().getKey();
            this.title = title;
        }

        public Builder description(String description){
            this.description = description;
            return this;
        }

        public Builder index(int index){
            this.index = index;
            return this;
        }

        public Builder lastModifiedBy(String lastModifiedBy){
            this.lastModifiedBy = lastModifiedBy;
            return this;
        }

        public Lesson build(){
            return new Lesson(this);
        }
    }
}
