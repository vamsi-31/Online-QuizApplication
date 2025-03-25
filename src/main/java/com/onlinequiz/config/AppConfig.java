package com.onlinequiz.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.onlinequiz.dao.QuestionDAO;
import com.onlinequiz.dao.QuizDAO;
import com.onlinequiz.dao.UserDAO;
import com.onlinequiz.dao.impl.QuestionDAOImpl;
import com.onlinequiz.dao.impl.QuizDAOImpl;
import com.onlinequiz.dao.impl.UserDAOImpl;
import com.onlinequiz.services.QuestionService;
import com.onlinequiz.services.QuizService;
import com.onlinequiz.services.UserService;
import com.onlinequiz.services.impl.QuestionServiceImpl;
import com.onlinequiz.services.impl.QuizServiceImpl;
import com.onlinequiz.services.impl.UserServiceImpl;
import com.onlinequiz.ui.ConsoleUI;

@Configuration
public class AppConfig {

    @Bean
    public QuestionDAO questionDAO() {
        return new QuestionDAOImpl();
    }

    @Bean
    public QuizDAO quizDAO() {
        return new QuizDAOImpl();
    }

    @Bean
    public UserDAO userDAO() {
        return new UserDAOImpl();
    }

    @Bean
    public QuestionService questionService(QuestionDAO questionDAO) {
        return new QuestionServiceImpl(questionDAO);
    }

    @Bean
    public QuizService quizService(QuizDAO quizDAO) {
        return new QuizServiceImpl(quizDAO);
    }

    @Bean
    public UserService userService(UserDAO userDAO) {
        return new UserServiceImpl(userDAO);
    }

    @Bean
    public ConsoleUI consoleUI(QuizService quizService, UserService userService, QuestionService questionService) {
        return new ConsoleUI(quizService, userService, questionService);
    }
}