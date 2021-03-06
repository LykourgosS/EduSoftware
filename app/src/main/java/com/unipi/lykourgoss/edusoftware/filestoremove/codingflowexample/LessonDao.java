package com.unipi.lykourgoss.edusoftware.filestoremove.codingflowexample;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * Created by LykourgosS <lpsarantidis@gmail.com>
 * on 17,June,2019.
 */

@Dao
public interface LessonDao {

    // methods' code will be auto-generated by Room library

    @Insert
    void insert(Lesson lesson);

    @Update
    void update(Lesson lesson);

    @Delete
    void delete(Lesson lesson);

    @Query("DELETE FROM lesson_table")
    void deleteAllLessons();

    @Query("SELECT * FROM lesson_table ORDER BY `index` ASC")
    LiveData<List<Lesson>> getAllLessons();
}
