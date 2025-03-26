package com.onlinequiz.models;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QuizTest {

    @Test
    void testQuizConstructorAndGetters() {
        String id = "QZ1";
        String title = "Geography Quiz";
        List<Question> questions = Arrays.asList(
                new Question("Q1", "Question 1", Arrays.asList("A", "B", "C"), 0, "Easy", Arrays.asList("Topic1"), 1),
                new Question("Q2", "Question 2", Arrays.asList("X", "Y", "Z"), 1, "Medium", Arrays.asList("Topic2"), 2)
        );
        int totalMarks = 3;
        String accessCode = "GEO123";
        boolean modifiable = true;

        Quiz quiz = new Quiz(id, title, questions, totalMarks, accessCode, modifiable);

        assertEquals(id, quiz.getId());
        assertEquals(title, quiz.getTitle());
        assertEquals(questions, quiz.getQuestions());
        assertEquals(totalMarks, quiz.getTotalMarks());
        assertEquals(accessCode, quiz.getAccessCode());
        assertTrue(quiz.isModifiable());
    }

    @Test
    void testQuizSetters() {
        Quiz quiz = new Quiz(null, null, null, 0, null, false);

        String id = "QZ2";
        String title = "History Quiz";
        List<Question> questions = Arrays.asList(
                new Question("Q3", "Question 3", Arrays.asList("D", "E", "F"), 2, "Hard", Arrays.asList("Topic3"), 3),
                new Question("Q4", "Question 4", Arrays.asList("P", "Q", "R"), 0, "Easy", Arrays.asList("Topic4"), 1)
        );
        int totalMarks = 4;
        String accessCode = "HIST456";
        boolean modifiable = true;

        quiz.setId(id);
        quiz.setTitle(title);
        quiz.setQuestions(questions);
        quiz.setTotalMarks(totalMarks);
        quiz.setAccessCode(accessCode);
        quiz.setModifiable(modifiable);

        assertEquals(id, quiz.getId());
        assertEquals(title, quiz.getTitle());
        assertEquals(questions, quiz.getQuestions());
        assertEquals(totalMarks, quiz.getTotalMarks());
        assertEquals(accessCode, quiz.getAccessCode());
        assertTrue(quiz.isModifiable());
    }
}