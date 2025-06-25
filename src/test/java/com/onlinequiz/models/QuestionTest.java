//package com.onlinequiz.models;
//
//import org.junit.jupiter.api.Test;
//import java.util.Arrays;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class QuestionTest {
//
//    @Test
//    void testQuestionConstructorAndGetters() {
//        String id = "Q1";
//        String title = "What is the capital of France?";
//        List<String> options = Arrays.asList("London", "Paris", "Berlin", "Madrid");
//        int correctOptionIndex = 1;
//        String difficulty = "Easy";
//        List<String> topics = Arrays.asList("Geography", "Europe");
//        int marks = 2;
//
//        Question question = new Question(id, title, options, correctOptionIndex, difficulty, topics, marks);
//
//        assertEquals(id, question.getId());
//        assertEquals(title, question.getTitle());
//        assertEquals(options, question.getOptions());
//        assertEquals(correctOptionIndex, question.getCorrectOptionIndex());
//        assertEquals(difficulty, question.getDifficulty());
//        assertEquals(topics, question.getTopics());
//        assertEquals(marks, question.getMarks());
//    }
//
//    @Test
//    void testQuestionSetters() {
//        Question question = new Question(null, null, null, 0, null, null, 0);
//
//        String id = "Q2";
//        String title = "What is the largest planet in our solar system?";
//        List<String> options = Arrays.asList("Mars", "Jupiter", "Saturn", "Neptune");
//        int correctOptionIndex = 1;
//        String difficulty = "Medium";
//        List<String> topics = Arrays.asList("Astronomy", "Solar System");
//        int marks = 3;
//
//        question.setId(id);
//        question.setTitle(title);
//        question.setOptions(options);
//        question.setCorrectOptionIndex(correctOptionIndex);
//        question.setDifficulty(difficulty);
//        question.setTopics(topics);
//        question.setMarks(marks);
//
//        assertEquals(id, question.getId());
//        assertEquals(title, question.getTitle());
//        assertEquals(options, question.getOptions());
//        assertEquals(correctOptionIndex, question.getCorrectOptionIndex());
//        assertEquals(difficulty, question.getDifficulty());
//        assertEquals(topics, question.getTopics());
//        assertEquals(marks, question.getMarks());
//    }
//}