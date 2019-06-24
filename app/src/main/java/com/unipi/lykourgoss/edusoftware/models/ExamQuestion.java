package com.unipi.lykourgoss.edusoftware.models;

import java.util.List;

/**
 * Created by LykourgosS <lpsarantidis@gmail.com>
 * on 25,June,2019.
 */

public class ExamQuestion {

    private String id;

    private String questionText; /* questionText or pdf */

    private String pdfUrl;

    private String pdfFilename;

    private List<String> answers;

    private String chapterId;
}
