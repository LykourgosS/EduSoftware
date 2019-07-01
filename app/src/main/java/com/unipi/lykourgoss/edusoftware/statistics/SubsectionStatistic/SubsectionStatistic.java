package com.unipi.lykourgoss.edusoftware.statistics.SubsectionStatistic;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.unipi.lykourgoss.edusoftware.Constant;

/**
 * Created by LykourgosS <lpsarantidis@gmail.com>
 * on 30,June,2019.
 */

@Entity(tableName = "subsection_statistic_table")
public class SubsectionStatistic {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String dateTime;

    private String subsectionId;

    private String subsectionTitle;

    private String subsectionPdfFilename;

    private long millisLeftOpen;

    public SubsectionStatistic(String subsectionId, String subsectionTitle, String subsectionPdfFilename, long millisLeftOpen) {
        this.dateTime = Constant.getCurrentTimeStamp();
        this.subsectionId = subsectionId;
        this.subsectionTitle = subsectionTitle;
        this.subsectionPdfFilename = subsectionPdfFilename;
        this.millisLeftOpen = millisLeftOpen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getSubsectionId() {
        return subsectionId;
    }

    public String getSubsectionTitle() {
        return subsectionTitle;
    }

    public String getSubsectionPdfFilename() {
        return subsectionPdfFilename;
    }

    public long getMillisLeftOpen() {
        return millisLeftOpen;
    }

    @Override
    public String toString() {
        return "SubsectionStatistic{" +
                "dateTime='" + dateTime + '\'' +
                ", subsectionId='" + subsectionId + '\'' +
                ", subsectionTitle='" + subsectionTitle + '\'' +
                ", subsectionPdfFilename='" + subsectionPdfFilename + '\'' +
                ", millisLeftOpen=" + millisLeftOpen +
                '}';
    }
}

