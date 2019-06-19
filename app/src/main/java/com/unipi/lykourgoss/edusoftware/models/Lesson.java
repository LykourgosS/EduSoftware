package com.unipi.lykourgoss.edusoftware.models;

import android.content.Context;
import androidx.annotation.NonNull;

import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.unipi.lykourgoss.edusoftware.Dialog;

import java.util.HashMap;
import java.util.Map;

public class Lesson extends EduEntity {

    //constant, Database child reference for Lessons
    public static final String LESSONS_REF = "Lessons";
    //todo undo initialization
    private static int childrenCount = 10;
//    public static final int LESSONS_FIELDS_LAYOUT = R.layout.fields_lesson;

    public Lesson() {
    }

    private Lesson(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.index = builder.index;
        this.lastModifiedBy = builder.lastModifiedBy;
    }

//    @Override
//    public int fieldsLayout() {
//        return LESSONS_FIELDS_LAYOUT;
//    }

    public static int childrenCount() {
        dbRef.child(LESSONS_REF).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                childrenCount = (int) dataSnapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return childrenCount;
    }

    @Override
    public void create(final Context context) {
        Dialog.progressbarAction(context, Dialog.CREATING);
        Map<String, Object> childUpdates = new HashMap<>();

        //stores this lesson object to firebase (/LESSONS_REF/id)
        childUpdates.put("/" + LESSONS_REF + "/" + id, this);
        dbRef.updateChildren(childUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Dialog.dismiss();
                        if (task.isSuccessful()){
                            Toast.makeText(context, "Lesson created", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
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
