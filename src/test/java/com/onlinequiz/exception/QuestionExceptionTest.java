package com.onlinequiz.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuestionExceptionTest {

    @Test
    void testQuestionExceptionWithMessage() {
        QuestionException exception = new QuestionException("Question not found");
        assertEquals("Question not found", exception.getMessage());
    }

    @Test
    void testQuestionExceptionWithMessageAndCause() {
        Throwable cause = new RuntimeException("Database error");
        QuestionException exception = new QuestionException("Failed to fetch question", cause);
        assertEquals("Failed to fetch question", exception.getMessage());
        assertEquals(cause, exception.getCause());
    }
}