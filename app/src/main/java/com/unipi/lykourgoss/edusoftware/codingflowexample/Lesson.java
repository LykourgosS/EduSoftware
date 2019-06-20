package com.unipi.lykourgoss.edusoftware.codingflowexample;

import android.content.Context;
import android.content.Intent;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by LykourgosS <lpsarantidis@gmail.com>
 * on 17,June,2019.
 */

@Entity(tableName = "lesson_table")
public class Lesson {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;

    //lesson does not need an index
    private int index;

    private String description;

    private String lastModifiedBy;

    public Lesson(String title, String description, int index, String lastModifiedBy) {
        this.title = title;
        this.index = index;
        this.description = description;
        this.lastModifiedBy = lastModifiedBy;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
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

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public Intent putToIntent(Context context, boolean toEdit){
        Intent intent = new Intent(context, CreateEditLessonActivity.class);
        intent.putExtra(CreateEditLessonActivity.EXTRA_ID, getId());
        intent.putExtra(CreateEditLessonActivity.EXTRA_TITLE, getTitle());
        intent.putExtra(CreateEditLessonActivity.EXTRA_INDEX, getIndex());
        intent.putExtra(CreateEditLessonActivity.EXTRA_DESCRIPTION, getDescription());
        return intent;
    }

    // returns a new lesson object to be stored in the database, in case of updating
    // (toUpdate = true) could return null if there is a problem getting lesson's id
    public static Lesson getFromIntent(Intent intent, boolean toUpdate, int defaultIndex){
        String title = intent.getStringExtra(CreateEditLessonActivity.EXTRA_TITLE);
        String description = intent.getStringExtra(CreateEditLessonActivity.EXTRA_DESCRIPTION);
        int index = intent.getIntExtra(CreateEditLessonActivity.EXTRA_INDEX, defaultIndex);
        Lesson lesson = new Lesson(title,description, index, ""/*todo getUserEmail*/);
        if (toUpdate){
            int id = intent.getIntExtra(CreateEditLessonActivity.EXTRA_ID, -1);
            // check if id = -1 => something went really wrong
            if (id == -1) {
                return null;
            }
            lesson.setId(id);
        }
        return lesson;
    }

    // equalsTo method for comparing lesson's properties, although probably have
    // different object reference (used for updating recyclerView adapter)
    public boolean equalsTo(Lesson otherLesson) {
        if (getId() == otherLesson.getId() &&
                getTitle() == otherLesson.getTitle() &&
                getDescription() == otherLesson.getDescription() &&
                getIndex() == otherLesson.getIndex() &&
                getLastModifiedBy() == otherLesson.getLastModifiedBy()){
            return true; //same lesson's properties
        } else {
            return false;
        }
    }
}
