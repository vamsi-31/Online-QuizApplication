package com.onlinequiz.ui;

import com.onlinequiz.models.Question;
import com.onlinequiz.models.Quiz;
import com.onlinequiz.models.User;
import com.onlinequiz.services.QuizService;
import com.onlinequiz.services.UserService;
import com.onlinequiz.services.QuestionService;
import com.onlinequiz.exception.QuizException;
import com.onlinequiz.exception.UserException;
import com.onlinequiz.exception.QuestionException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class ConsoleUI {
    private final QuizService quizService;
    private final UserService userService;
    private final QuestionService questionService;
    private final Scanner scanner;
    private User currentUser;
    private static final Logger logger = LoggerFactory.getLogger(ConsoleUI.class);

    public ConsoleUI(QuizService quizService, UserService userService, QuestionService questionService) {
        this.quizService = quizService;
        this.userService = userService;
        this.questionService = questionService;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        logger.info("Starting ConsoleUI");
        while (true) {
            try {
                if (currentUser == null) {
                    showLoginMenu();
                } else if (currentUser.getRole().equals("ADMIN")) {
                    showAdminMenu();
                } else {
                    showUserMenu();
                }
            } catch (QuizException | UserException | QuestionException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
        }
    }

    private void showLoginMenu() {
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
        System.out.print("Choose an option: ");
        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 1:
                login();
                break;
            case 2:
                register();
                break;
            case 3:
                System.exit(0);
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }

    private void login() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        Optional<User> userOptional = userService.authenticateUser(username, password);
        if (userOptional.isPresent()) {
            currentUser = userOptional.get();
            System.out.println("Login successful!");
        } else {
            System.out.println("Invalid username or password.");
        }
    }

    private void register() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Enter role (ADMIN/USER): ");
        String role = scanner.nextLine().toUpperCase();

        try {
            User newUser = userService.createUser(username, password, role);
            System.out.println("User registered successfully!");
        } catch (UserException e) {
            System.out.println(e.getMessage());
        }
    }

    private void showAdminMenu() {
        System.out.println("1. Create Quiz");
        System.out.println("2. View All Quizzes");
        System.out.println("3. Lock Quiz");
        System.out.println("4. Create Question");
        System.out.println("5. View All Questions");
        System.out.println("6. Logout");
        System.out.print("Choose an option: ");
        int choice = Integer.parseInt(scanner.nextLine());

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
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }

    private void showUserMenu() {
        System.out.println("1. Take Quiz");
        System.out.println("2. View Quiz Results");
        System.out.println("3. Logout");
        System.out.print("Choose an option: ");
        int choice = Integer.parseInt(scanner.nextLine());

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
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }

    private void createQuiz() {
        System.out.print("Enter quiz title: ");
        String title = scanner.nextLine();

        List<Question> questions = new ArrayList<>();
        while (true) {
            System.out.print("Enter question ID to add to the quiz (or 'done' to finish): ");
            String questionId = scanner.nextLine();
            if (questionId.equalsIgnoreCase("done")) {
                break;
            }

            Optional<Question> questionOpt = questionService.getQuestionById(questionId);
            if (questionOpt.isPresent()) {
                questions.add(questionOpt.get());
            } else {
                System.out.println("Question not found. Please try again.");
            }
        }

        try {
            Quiz createdQuiz = quizService.createQuiz(title, questions);
            System.out.println("Quiz created successfully! Access code: " + createdQuiz.getAccessCode());
        } catch (QuizException e) {
            System.out.println("Error creating quiz: " + e.getMessage());
        }
    }

    private void viewAllQuizzes() {
        List<Quiz> quizzes = quizService.getAllQuizzes();
        for (Quiz quiz : quizzes) {
            System.out.println("ID: " + quiz.getId() + ", Title: " + quiz.getTitle() + ", Total Marks: " + quiz.getTotalMarks());
        }
    }

    private void lockQuiz() {
        System.out.print("Enter quiz ID to lock: ");
        String quizId = scanner.nextLine();
        try {
            quizService.lockQuiz(quizId);
            System.out.println("Quiz locked successfully!");
        } catch (QuizException e) {
            System.out.println(e.getMessage());
        }
    }

    private void createQuestion() {
        System.out.print("Enter question title: ");
        String title = scanner.nextLine();

        List<String> options = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            System.out.print("Enter option " + i + ": ");
            options.add(scanner.nextLine());
        }

        System.out.print("Enter the index of the correct option (1-4): ");
        int correctOptionIndex = Integer.parseInt(scanner.nextLine()) - 1;

        System.out.print("Enter difficulty (EASY/MEDIUM/HARD): ");
        String difficulty = scanner.nextLine().toUpperCase();

        System.out.print("Enter topics (comma-separated): ");
        List<String> topics = List.of(scanner.nextLine().split(","));

        System.out.print("Enter marks for this question: ");
        int marks = Integer.parseInt(scanner.nextLine());

        Question question = new Question(null, title, options, correctOptionIndex, difficulty, topics, marks);
        try {
            Question createdQuestion = questionService.createQuestion(question);
            System.out.println("Question created successfully! ID: " + createdQuestion.getId());
        } catch (QuestionException e) {
            System.out.println("Error creating question: " + e.getMessage());
        }
    }

    private void viewAllQuestions() {
        List<Question> questions = questionService.getAllQuestions();
        for (Question question : questions) {
            System.out.println("ID: " + question.getId() + ", Title: " + question.getTitle() + ", Difficulty: " + question.getDifficulty());
        }
    }

    private void takeQuiz() {
        System.out.print("Enter quiz access code: ");
        String accessCode = scanner.nextLine();

        Optional<Quiz> quizOpt = quizService.getQuizByAccessCode(accessCode);
        if (quizOpt.isPresent()) {
            Quiz quiz = quizOpt.get();
            int score = 0;

            for (Question question : quiz.getQuestions()) {
                System.out.println(question.getTitle());
                List<String> options = question.getOptions();
                for (int i = 0; i < options.size(); i++) {
                    System.out.println((i + 1) + ". " + options.get(i));
                }

                System.out.print("Enter your answer (1-" + options.size() + "): ");
                int userAnswer = Integer.parseInt(scanner.nextLine()) - 1;

                if (userAnswer == question.getCorrectOptionIndex()) {
                    score += question.getMarks();
                }
            }

            System.out.println("Quiz completed! Your score: " + score + "/" + quiz.getTotalMarks());
        } else {
            System.out.println("Quiz not found. Please check the access code.");
        }
    }

    private void viewQuizResults() {
        System.out.println("Quiz results viewing is not implemented in this version.");
    }

    private void logout() {
        currentUser = null;
        System.out.println("Logged out successfully.");
    }
}