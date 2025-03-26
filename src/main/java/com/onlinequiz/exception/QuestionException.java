package com.onlinequiz.exception;

public class QuestionException extends RuntimeException {
    public QuestionException(String message) {
        super(message);
    }

    public QuestionException(String message, Throwable cause) {
        super(message, cause);
    }
}