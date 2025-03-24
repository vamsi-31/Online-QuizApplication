package com.onlinequiz;

import com.onlinequiz.dao.QuizDAO;
import com.onlinequiz.dao.UserDAO;
import com.onlinequiz.dao.QuestionDAO;
import com.onlinequiz.dao.impl.QuizDAOImpl;
import com.onlinequiz.dao.impl.UserDAOImpl;
import com.onlinequiz.dao.impl.QuestionDAOImpl;
import com.onlinequiz.services.QuizService;
import com.onlinequiz.services.UserService;
import com.onlinequiz.services.QuestionService;
import com.onlinequiz.services.impl.QuizServiceImpl;
import com.onlinequiz.services.impl.UserServiceImpl;
import com.onlinequiz.services.impl.QuestionServiceImpl;
import com.onlinequiz.ui.ConsoleUI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {
        logger.info("Starting Online Quiz Application");
        try {
            // Initialize DAOs
            QuizDAO quizDAO = new QuizDAOImpl();
            UserDAO userDAO = new UserDAOImpl();
            QuestionDAO questionDAO = new QuestionDAOImpl();

            // Initialize Services
            QuizService quizService = new QuizServiceImpl(quizDAO);
            UserService userService = new UserServiceImpl(userDAO);
            QuestionService questionService = new QuestionServiceImpl(questionDAO);

            // Initialize and start ConsoleUI
            ConsoleUI consoleUI = new ConsoleUI(quizService, userService, questionService);
            consoleUI.start();
        } catch (Exception e) {
            logger.error("Fatal error: {}", e.getMessage(), e);
            System.out.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}