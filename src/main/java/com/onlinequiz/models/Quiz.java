package com.onlinequiz.models;

import java.util.List;

/**
 * Represents a quiz in the application.
 */
public class Quiz {
    private String id;
    private String title;
    private List<Question> questions;
    private int totalMarks;
    private String accessCode;
    private boolean modifiable;

    // Constructor
    public Quiz(){

    }
    public Quiz(String id, String title, List<Question> questions, int totalMarks, String accessCode, boolean modifiable) {
        this.id = id;
        this.title = title;
        this.questions = questions;
        this.totalMarks = totalMarks;
        this.accessCode = accessCode;
        this.modifiable = modifiable;
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public int getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(int totalMarks) {
        this.totalMarks = totalMarks;
    }

    public String getAccessCode() {
        return accessCode;
    }

    public void setAccessCode(String accessCode) {
        this.accessCode = accessCode;
    }

    public boolean isModifiable() {
        return modifiable;
    }

    public void setModifiable(boolean modifiable) {
        this.modifiable = modifiable;
    }
}