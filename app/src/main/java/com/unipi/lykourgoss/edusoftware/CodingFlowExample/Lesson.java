package com.unipi.lykourgoss.edusoftware.CodingFlowExample;

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
}
