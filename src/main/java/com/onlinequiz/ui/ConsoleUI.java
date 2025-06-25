package com.onlinequiz.ui;

import com.onlinequiz.exception.QuestionException;
import com.onlinequiz.exception.QuizException;
import com.onlinequiz.models.Question;
import com.onlinequiz.models.Quiz;
import com.onlinequiz.models.User;
import com.onlinequiz.services.QuizService;
import com.onlinequiz.services.UserService;
import com.onlinequiz.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static com.onlinequiz.constants.Constants.*;

@Component
public class ConsoleUI {
    private final QuizService quizService;
    private final UserService userService;
    private final QuestionService questionService;
    private final Scanner scanner;
    private static final Logger logger = LoggerFactory.getLogger(ConsoleUI.class);
    private User currentUser;
    private boolean isRunning;

    @Autowired
    public ConsoleUI(QuizService quizService, UserService userService, QuestionService questionService) {
        this.quizService = quizService;
        this.userService = userService;
        this.questionService = questionService;
        this.scanner = new Scanner(System.in);
        this.isRunning = true;
        logger.info("ConsoleUI initialized");
    }

    public void start() {
        logger.info("Starting ConsoleUI");
        while (isRunning) {
            try {
                if (currentUser == null) {
                    showLoginMenu();
                } else if (currentUser.getRole().equals(ROLE_ADMIN)) {
                    showAdminMenu();
                } else {
                    showUserMenu();
                }
            } catch (Exception e) {
                logger.error("An error occurred: {}", e.getMessage());
                printError("An error occurred: " + e.getMessage());
            }
        }
        logger.info("ConsoleUI stopped");
    }

    private void showLoginMenu() {
        printHeader("Login Menu");
        printOption("1", "Login");
        printOption("2", "Register");
        printOption("3", "Exit");
        int choice = getIntInput(INPUT_OPTION);

        switch (choice) {
            case 1:
                login();
                break;
            case 2:
                register();
                break;
            case 3:
                exit();
                break;
            default:
                logger.warn("Invalid option selected: {}", choice);
                printError(INVALID_OPTION);
        }
    }

    private void login() {
        logger.debug("Attempting login");
        printHeader("Login");
        String username = getStringInput(INPUT_USERNAME);
        String password = getStringInput(INPUT_PASSWORD);

        try {
            currentUser = userService.login(username, password);
            logger.info("User {} logged in successfully", username);
            printSuccess("Login successful!");
        } catch (Exception e) {
            logger.warn("Login failed for username: {}. Error: {}", username, e.getMessage());
            printError("Login failed: " + e.getMessage());
        }
    }

    private void register() {
        logger.debug("Attempting user registration");
        printHeader("Register");
        String username = getStringInput(INPUT_USERNAME);
        String password = getStringInput(INPUT_PASSWORD);
        String role = getStringInput("Enter role (ADMIN/USER): ").toUpperCase();

        try {
            User newUser = userService.register(username, password, role);
            logger.info("New user registered: {}", username);
            printSuccess("User registered successfully!");
        } catch (Exception e) {
            logger.warn("Registration failed for username: {}. Error: {}", username, e.getMessage());
            printError("Registration failed: " + e.getMessage());
        }
    }

    private void showAdminMenu() {
        logger.debug("Showing admin menu");
        printHeader("Admin Menu");
        printOption("1", "Create Quiz");
        printOption("2", "View All Quizzes");
        printOption("3", "Lock Quiz");
        printOption("4", "Create Question");
        printOption("5", "View All Questions");
        printOption("6", "Logout");
        printOption("7", "Exit");
        int choice = getIntInput(INPUT_OPTION);

        switch (choice) {
            case 1:
                createQuiz();
                break;
            case 2:
                viewAllQuizzes();
                break;
            case 3:
                lockQuiz();
                break;
            case 4:
                createQuestion();
                break;
            case 5:
                viewAllQuestions();
                break;
            case 6:
                logout();
                break;
            case 7:
                exit();
                break;
            default:
                logger.warn("Invalid admin menu option selected: {}", choice);
                printError(INVALID_OPTION);
        }
    }

    private void showUserMenu() {
        logger.debug("Showing user menu");
        printHeader("User Menu");
        printOption("1", "Take Quiz");
        printOption("2", "View Quiz Results");
        printOption("3", "Logout");
        printOption("4", "Exit");
        int choice = getIntInput(INPUT_OPTION);

        switch (choice) {
            case 1:
                takeQuiz();
                break;
            case 2:
                viewQuizResults();
                break;
            case 3:
                logout();
                break;
            case 4:
                exit();
                break;
            default:
                logger.warn("Invalid user menu option selected: {}", choice);
                printError(INVALID_OPTION);
        }
    }

    private void createQuiz() {
        logger.debug("Creating a new quiz");
        printHeader("Create Quiz");
        String title = getStringInput("Enter quiz title: ");
        List<String> questionIds = new ArrayList<>();
        while (true) {
            String questionId = getStringInput("Enter question ID to add to the quiz (or 'done' to finish): ");
            if (questionId.equalsIgnoreCase("done")) {
                break;
            }
            questionIds.add(questionId);
        }

        try {
            Quiz createdQuiz = quizService.createQuiz(title, questionIds);
            logger.info("Quiz created successfully: {}", createdQuiz.getTitle());
            printSuccess("Quiz created successfully! Access code: " + createdQuiz.getAccessCode());
        } catch (QuizException e) {
            logger.warn("Failed to create quiz: {}. Error: {}", title, e.getMessage());
            printError("Failed to create quiz: " + e.getMessage());
        }
    }

    private void takeQuiz() {
        logger.debug("User taking a quiz");
        printHeader("Take Quiz");
        String accessCode = getStringInput("Enter quiz access code: ");

        try {
            Quiz quiz = quizService.getQuizByAccessCode(accessCode)
                    .orElseThrow(() -> new QuizException("Quiz not found"));
            List<Integer> userAnswers = new ArrayList<>();
            for (int i = 0; i < quiz.getQuestions().size(); i++) {
                Question question = quiz.getQuestions().get(i);
                printInfo("Question " + (i + 1) + ": " + question.getTitle());
                for (int j = 0; j < question.getOptions().size(); j++) {
                    printInfo((j + 1) + ". " + question.getOptions().get(j));
                }
                int answer = getIntInput("Enter your answer (1-" + question.getOptions().size() + "): ");
                userAnswers.add(answer - 1);
            }

            int score = quizService.takeQuiz(accessCode, userAnswers);
            logger.info("User completed quiz with access code: {}. Score: {}", accessCode, score);
            printSuccess("Quiz completed! Your score: " + score);
        } catch (QuizException e) {
            logger.error("Error taking quiz: {}", e.getMessage());
            printError("Error taking quiz: " + e.getMessage());
        }
    }

    private void viewAllQuizzes() {
        logger.debug("Viewing all quizzes");
        printHeader("All Quizzes");
        List<Quiz> quizzes = quizService.getAllQuizzes();
        if (quizzes.isEmpty()) {
            printInfo("No quizzes found.");
        } else {
            for (Quiz quiz : quizzes) {
                printInfo(String.format("ID: %s, Title: %s, Total Marks: %d, Access Code: %s",
                        quiz.getId(), quiz.getTitle(), quiz.getTotalMarks(), quiz.getAccessCode()));
            }
        }
    }

    private void lockQuiz() {
        logger.debug("Attempting to lock a quiz");
        printHeader("Lock Quiz");
        String quizId = getStringInput("Enter quiz ID to lock: ");
        try {
            quizService.lockQuiz(quizId);
            logger.info("Quiz locked successfully: {}", quizId);
            printSuccess("Quiz locked successfully!");
        } catch (QuizException e) {
            logger.error("Failed to lock quiz: {}. Error: {}", quizId, e.getMessage());
            printError(e.getMessage());
        }
    }

    private void createQuestion() {
        logger.debug("Creating a new question");
        printHeader("Create Question");
        try {
            String title = getStringInput("Enter question title: ");

            List<String> options = new ArrayList<>();
            for (int i = 1; i <= 4; i++) {
                options.add(getStringInput("Enter option " + i + ": "));
            }
            int correctOptionIndex = getIntInput("Enter the index of the correct option (1-4): ") - 1;
            String difficulty = getStringInput("Enter difficulty (EASY/MEDIUM/HARD): ").toUpperCase();
            List<String> topics = Arrays.asList(getStringInput("Enter topics (comma-separated): ").split(","));
            int marks = getIntInput("Enter marks for this question: ");

            Question question = Question.builder()
                    .title(title)
                    .options(options)
                    .correctOptionIndex(correctOptionIndex)
                    .difficulty(difficulty)
                    .topics(topics)
                    .marks(marks)
                    .build();

            Question createdQuestion = questionService.createQuestion(question);
            logger.info("Question created successfully: {}", createdQuestion.getId());
            printSuccess("Question created successfully! ID: " + createdQuestion.getId());
        } catch (QuestionException e) {
            logger.error("Error creating question: {}", e.getMessage());
            printError("Error creating question: " + e.getMessage());
        } catch (NumberFormatException e) {
            logger.error("Invalid input for question creation: {}", e.getMessage());
            printError("Invalid input. Please enter a valid number for correct option index or marks.");
        }
    }

    private void viewAllQuestions() {
        logger.debug("Viewing all questions");
        printHeader("All Questions");
        List<Question> questions = questionService.getAllQuestions();
        for (Question question : questions) {
            printInfo(String.format("ID: %s, Title: %s, Difficulty: %s", question.getId(), question.getTitle(), question.getDifficulty()));
        }
    }

    private void viewQuizResults() {
        logger.debug("Viewing quiz results");
        printHeader("Quiz Results");
        printInfo("Pending task in this version will be implemented in next version update.");
    }

    private void logout() {
        logger.debug("User logging out: {}", currentUser.getUsername());
        currentUser = null;
        printSuccess("Logged out successfully.");
    }

    private void exit() {
        logger.info("User chose to exit the application");
        isRunning = false;
        printSuccess("Thank you for using the Online Quiz System. Goodbye!");
    }

    // Helper methods for improved console output
    private void printHeader(String header) {
        logger.debug("Printing header: {}", header);
        logger.info("\n===== {} =====", header);
    }

    private void printOption(String key, String value) {
        logger.info("{}. {}", key, value);
    }

    private void printSuccess(String message) {
        logger.info(message);
        logger.info("\n✅ {}", message);
    }

    private void printError(String message) {
        logger.error(message);
        logger.info("\n❌ {}", message);
    }

    private void printInfo(String message) {
        logger.info(message);
        logger.info("ℹ️ {}", message);
    }

    private String getStringInput(String prompt) {
        logger.info(prompt);
        return scanner.nextLine();
    }

    private int getIntInput(String prompt) {
        logger.info(prompt);
        return Integer.parseInt(scanner.nextLine());
    }
}