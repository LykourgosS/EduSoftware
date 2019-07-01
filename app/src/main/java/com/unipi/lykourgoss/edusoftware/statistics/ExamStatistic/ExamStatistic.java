package com.unipi.lykourgoss.edusoftware.statistics.ExamStatistic;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.unipi.lykourgoss.edusoftware.Constant;

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

    private int score;

    private int questionCount;

    private long millisDuration;

    private boolean passTest;

    private boolean completed;

    public ExamStatistic(String chapterId, String chapterTitle, int score, int questionCount, long millisDuration, boolean completed) {
        this.dateTime = Constant.getCurrentTimeStamp();
        this.chapterId = chapterId;
        this.chapterTitle = chapterTitle;
        this.score = score;
        this.questionCount = questionCount;
        this.millisDuration = millisDuration;
        this.passTest = completed && (double) score > (double) (questionCount / 2); // pass: at least half answers correct
        this.completed = completed;
    }

    public int getId() {
        return id;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getChapterId() {
        return chapterId;
    }

    public String getChapterTitle() {
        return chapterTitle;
    }

    public int getScore() {
        return score;
    }

    public int getQuestionCount() {
        return questionCount;
    }

    public long getMillisDuration() {
        return millisDuration;
    }

    public boolean isPassTest() {
        return passTest;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public void setPassTest(boolean passTest) {
        this.passTest = passTest;
    }

    @Override
    public String toString() {
        return "ExamStatistic{" +
                "dateTime='" + dateTime + '\'' +
                ", chapterId='" + chapterId + '\'' +
                ", chapterTitle='" + chapterTitle + '\'' +
                ", score=" + score +
                ", questionCount=" + questionCount +
                ", millisDuration=" + millisDuration +
                ", passTest=" + passTest +
                ", completed=" + completed +
                '}';
    }
}
