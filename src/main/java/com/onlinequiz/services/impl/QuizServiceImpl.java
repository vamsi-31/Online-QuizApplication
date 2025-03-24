package com.onlinequiz.services.impl;

import com.onlinequiz.dao.QuizDAO;
import com.onlinequiz.models.Quiz;
import com.onlinequiz.models.Question;
import com.onlinequiz.services.QuizService;
import com.onlinequiz.exception.QuizException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class QuizServiceImpl implements QuizService {
    private static final Logger logger = LoggerFactory.getLogger(QuizServiceImpl.class);
    private final QuizDAO quizDAO;

    public QuizServiceImpl(QuizDAO quizDAO) {
        logger.info("QuizServiceImpl initialized");
        this.quizDAO = quizDAO;
    }

    @Override
    public Quiz createQuiz(String title, List<Question> questions) {
        logger.info("Creating quiz: {}", title);
        if (title == null || title.trim().isEmpty()) {
            throw new QuizException("Quiz title cannot be empty");
        }
        if (questions == null || questions.isEmpty()) {
            throw new QuizException("Quiz must have at least one question");
        }

        int totalMarks = questions.stream().mapToInt(Question::getMarks).sum();
        String accessCode = generateAccessCode();
        Quiz quiz = new Quiz(UUID.randomUUID().toString(), title, questions, totalMarks, accessCode, true);
        return quizDAO.createQuiz(quiz);
    }

    @Override
    public Optional<Quiz> getQuizById(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new QuizException("Quiz ID cannot be empty");
        }
        return quizDAO.getQuizById(id);
    }

    @Override
    public List<Quiz> getAllQuizzes() {
        return quizDAO.getAllQuizzes();
    }

    @Override
    public Quiz updateQuiz(Quiz quiz) {
        if (quiz == null) {
            throw new QuizException("Quiz cannot be null");
        }
        if (!quiz.isModifiable()) {
            throw new QuizException("This quiz is no longer modifiable.");
        }
        return quizDAO.updateQuiz(quiz);
    }

    @Override
    public boolean deleteQuiz(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new QuizException("Quiz ID cannot be empty");
        }
        return quizDAO.deleteQuiz(id);
    }

    @Override
    public Optional<Quiz> getQuizByAccessCode(String accessCode) {
        if (accessCode == null || accessCode.trim().isEmpty()) {
            throw new QuizException("Access code cannot be empty");
        }
        return quizDAO.getQuizByAccessCode(accessCode);
    }

    @Override
    public void lockQuiz(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new QuizException("Quiz ID cannot be empty");
        }
        Optional<Quiz> quizOpt = quizDAO.getQuizById(id);
        if (quizOpt.isPresent()) {
            Quiz quiz = quizOpt.get();
            quiz.setModifiable(false);
            quizDAO.updateQuiz(quiz);
        } else {
            throw new QuizException("Quiz not found");
        }
    }

    private String generateAccessCode() {
        return UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    }
}
