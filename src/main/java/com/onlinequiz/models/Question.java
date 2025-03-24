package com.onlinequiz.models;

import java.util.List;

/**
 * Represents a question in the quiz application.
 */
public class Question {
    private String id;
    private String title;
    private List<String> options;
    private int correctOptionIndex;
    private String difficulty;
    private List<String> topics;
    private int marks;

    // Constructor
    public Question(String id, String title, List<String> options, int correctOptionIndex, String difficulty, List<String> topics, int marks) {
        this.id = id;
        this.title = title;
        this.options = options;
        this.correctOptionIndex = correctOptionIndex;
        this.difficulty = difficulty;
        this.topics = topics;
        this.marks = marks;
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

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public int getCorrectOptionIndex() {
        return correctOptionIndex;
    }

    public void setCorrectOptionIndex(int correctOptionIndex) {
        this.correctOptionIndex = correctOptionIndex;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public List<String> getTopics() {
        return topics;
    }

    public void setTopics(List<String> topics) {
        this.topics = topics;
    }

    public int getMarks() {
        return marks;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }
}