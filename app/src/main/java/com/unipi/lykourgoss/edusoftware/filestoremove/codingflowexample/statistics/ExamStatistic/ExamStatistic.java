package com.unipi.lykourgoss.edusoftware.filestoremove.codingflowexample.statistics.ExamStatistic;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by LykourgosS <lpsarantidis@gmail.com>
 * on 01,July,2019.
 */

@Entity(tableName = "exam_statistic_table")
public class ExamStatistic {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String dateTime;

    private String chapterId;

    private String chapterTitle;

    private int correctAnswers;

    private int answerCount;

    private int secondsLeftOpen;

    private boolean pass;
}
