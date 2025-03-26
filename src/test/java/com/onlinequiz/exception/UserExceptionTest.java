package com.onlinequiz.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

 class UserExceptionTest {

    @Test
     void testUserExceptionWithMessage() {
        UserException exception = new UserException("User not found");
        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void testUserExceptionWithMessageAndCause() {
        Throwable cause = new RuntimeException("Authentication failed");
        UserException exception = new UserException("Failed to login user", cause);
        assertEquals("Failed to login user", exception.getMessage());
        assertEquals(cause, exception.getCause());
    }
}