package com.unipi.lykourgoss.edusoftware.models;

import java.util.List;

/**
 * Created by LykourgosS <lpsarantidis@gmail.com>
 * on 25,June,2019.
 */

public class Question {

    public static final String _PARENT_ID = "parentId";

    public static final String _QUESTIONS_REF = "/questions";

    private String id;

    private String questionText; /* questionText or pdf */

    private String correctAnswer;

    private List<String> wrongAnswers; // list of 4 wrongAnswers

    private String parentId;

    //private String pdfUrl;

    //private String pdfFilename;

    public Question() {
    }

    public Question(String id, String questionText, String correctAnswer, List<String> wrongAnswers, String parentId) {
        this.id = id;
        this.questionText = questionText;
        this.correctAnswer = correctAnswer;
        this.wrongAnswers = wrongAnswers;
        this.parentId = parentId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public List<String> getWrongAnswers() {
        return wrongAnswers;
    }

    public void setWrongAnswers(List<String> wrongAnswers) {
        this.wrongAnswers = wrongAnswers;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public boolean equalsTo(Question question) {
        return getId().equals(question.getId()) &&
                getQuestionText().equals(question.getQuestionText()) &&
                getCorrectAnswer() == question.getCorrectAnswer() &&
                getWrongAnswers().equals(question.getWrongAnswers()) &&
                getParentId().equals(question.getParentId());
    }
}
