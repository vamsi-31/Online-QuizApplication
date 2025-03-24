package com.onlinequiz.services.impl;

import com.onlinequiz.dao.QuestionDAO;
import com.onlinequiz.exception.QuestionException;
import com.onlinequiz.models.Question;
import com.onlinequiz.services.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class QuestionServiceImpl implements QuestionService {
    private static final Logger logger = LoggerFactory.getLogger(QuizServiceImpl.class);
    private final QuestionDAO questionDAO;

    public QuestionServiceImpl(QuestionDAO questionDAO) {
        logger.info("QuizServiceImpl initialized");
        this.questionDAO = questionDAO;
    }

    @Override
    public Question createQuestion(Question question) {
        logger.info("Creating question: {}", question.getTitle());
        if (question == null) {
            throw new QuestionException("Question cannot be null");
        }
        if (question.getTitle() == null || question.getTitle().trim().isEmpty()) {
            throw new QuestionException("Question title cannot be empty");
        }
        if (question.getOptions() == null || question.getOptions().size() < 2) {
            throw new QuestionException("Question must have at least two options");
        }
        if (question.getCorrectOptionIndex() < 0 || question.getCorrectOptionIndex() >= question.getOptions().size()) {
            throw new QuestionException("Invalid correct option index");
        }

        question.setId(UUID.randomUUID().toString());
        return questionDAO.createQuestion(question);
    }

    @Override
    public Optional<Question> getQuestionById(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new QuestionException("Question ID cannot be empty");
        }
        return questionDAO.getQuestionById(id);
    }

    @Override
    public List<Question> getAllQuestions() {
        return questionDAO.getAllQuestions();
    }

    @Override
    public Question updateQuestion(Question question) {
        if (question == null) {
            throw new QuestionException("Question cannot be null");
        }
        if (question.getId() == null || question.getId().trim().isEmpty()) {
            throw new QuestionException("Question ID cannot be empty");
        }
        return questionDAO.updateQuestion(question);
    }

    @Override
    public boolean deleteQuestion(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new QuestionException("Question ID cannot be empty");
        }
        return questionDAO.deleteQuestion(id);
    }
}
