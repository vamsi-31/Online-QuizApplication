package com.onlinequiz.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

 class QuizExceptionTest {

    @Test
    void testQuizExceptionWithMessage() {
        QuizException exception = new QuizException("Quiz not found");
        assertEquals("Quiz not found", exception.getMessage());
    }

    @Test
    void testQuizExceptionWithMessageAndCause() {
        Throwable cause = new RuntimeException("Invalid quiz data");
        QuizException exception = new QuizException("Failed to create quiz", cause);
        assertEquals("Failed to create quiz", exception.getMessage());
        assertEquals(cause, exception.getCause());
    }
}