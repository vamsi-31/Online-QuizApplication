package com.onlinequiz.constants;

public class Constants {
    public static final String ROLE_ADMIN = "ADMIN";
    public static final String DIFFICULTY_EASY = "EASY";
    public static final String DIFFICULTY_MEDIUM = "MEDIUM";
    public static final String DIFFICULTY_HARD = "HARD";
    public static final int MIN_PASSWORD_LENGTH = 8;// .next verision update feauture
    public static final int MAX_QUIZ_QUESTIONS = 50;// .next verision update feauture
    public static final String INPUT_OPTION="Choose an option: ";
    public static final String INVALID_OPTION="Invalid option. Please try again.";
    public static final String INPUT_USERNAME="Enter username: ";
    public static final String INPUT_PASSWORD="Enter password: ";
    public static final String  ERROR_EMPTY_USERNAME="User Name cannot be empty.";
    public static final String  ERROR_EMPTY_USER="User Name cannot be NULL.";
    public static final String  ERROR_EMPTY_PASSWORD="Password cannot be empty.";
    public static final String  ERROR_EMPTY_QUIZ_MODIFY="This quiz is no longer modifiable.";
    public static final String  ERROR_EMPTY_QUIZ_ID="Quiz Id cannot be empty.";
    public static final String  ERROR_EMPTY_TITLE="Question title cannot be null or empty";
    public static final String  ERROR_INVALID_OPTIONS="Question must have at least two options";
    public static final String  ERROR_INVALID_TOPICS="Topics cannot be null or empty";
    public static final String  ERROR_INVALID_MARKS="Marks must be greater than zero";
    public static final String  ERROR_INVALID_CORRECT_OPTIONS_INDEX="Invalid correct option index";
    public static final String  ERROR_EMPTY_QUESTION_ID="Question Id cannot be empty or null.";
    public static final String  ERROR_INVALID_QUESTION_ID="Question Id is not valid.";
    public static final String  ERROR_INVALID_DIFFICULTY_LEVEL="Difficulty is not valid.";
    public static final String  ERROR_NULL_QUIZ_ID="Quiz Id cannot be Null";
    public static final String  ERROR_EMPTY_ACCESS_CODE="Access code cannot be Empty";
    public static final String ERROR_INVALID_CREDENTIALS = "Invalid username or password";//
    public static final String ERROR_EMPTY_USER_ID = "User ID cannot be empty";
    public static final String ERROR_USER_EXISTS = "Username already exists";
    public static final String ERROR_INVALID_ROLE = "Invalid role. Must be either ADMIN or USER";
    public static final String ERROR_QUIZ_NOT_FOUND = "Quiz not found";//
    public static final String ERROR_QUESTION_NOT_FOUND = "Question not found";
    public static final String ERROR_INVALID_ACCESS_CODE = "Quiz not found Check the access code";

private Constants() {

}}
