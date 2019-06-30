package com.unipi.lykourgoss.edusoftware.filestoremove.codingflowexample.statistics.SubsectionStatistic;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

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

    private int secondsLeftOpen;
}

