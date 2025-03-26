package com.onlinequiz.services.impl;

import com.onlinequiz.dao.QuestionDAO;
import com.onlinequiz.exception.QuestionException;
import com.onlinequiz.models.Question;
import com.onlinequiz.services.QuestionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.onlinequiz.constants.Constants.*;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionDAO questionDAO;

    public QuestionServiceImpl(QuestionDAO questionDAO) {
        this.questionDAO = questionDAO;
    }

    @Override
    public Question createQuestion(String title, List<String> options, int correctOptionIndex, String difficulty, List<String> topics, int marks) {
        validateQuestionInput(title, options, correctOptionIndex, difficulty, topics, marks);
        Question question = new Question(UUID.randomUUID().toString(), title, options, correctOptionIndex, difficulty, topics, marks);
        return questionDAO.createQuestion(question);
    }


    @Override
    public Optional<Question> getQuestionById(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new QuestionException(ERROR_EMPTY_QUESTION_ID);
        }
        return questionDAO.getQuestionById(id);
    }

    @Override
    public List<Question> getAllQuestions() {
        return questionDAO.getAllQuestions();
    }

    @Override
    public Question updateQuestion(Question question) {
        if (question == null || question.getId() == null || question.getId().trim().isEmpty()) {
            throw new QuestionException(ERROR_INVALID_QUESTION_ID);
        }
        validateQuestion(question);
        return questionDAO.updateQuestion(question);
    }

    @Override
    public boolean isDeleteQuestion(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new QuestionException(ERROR_EMPTY_QUESTION_ID);
        }
        return questionDAO.isDeleteQuestion(id);
    }

    private void validateQuestion(Question question) {
        validateQuestionInput(question.getTitle(), question.getOptions(), question.getCorrectOptionIndex(),
                question.getDifficulty(), question.getTopics(), question.getMarks());
    }

    private void validateQuestionInput(String title, List<String> options, int correctOptionIndex, String difficulty, List<String> topics, int marks) {
        if (title == null || title.trim().isEmpty()) {
            throw new QuestionException(ERROR_EMPTY_TITLE);
        }
        if (options == null || options.size() < 2) {
            throw new QuestionException(ERROR_INVALID_OPTIONS);
        }
        if (correctOptionIndex < 0 || correctOptionIndex >= options.size()) {
            throw new QuestionException(ERROR_INVALID_CORRECT_OPTIONS_INDEX);
        }
        if (!isValidDifficulty(difficulty)) {
            throw new QuestionException(ERROR_INVALID_DIFFICULTY_LEVEL);
        }
        if (topics == null || topics.isEmpty()) {
            throw new QuestionException(ERROR_INVALID_TOPICS);
        }
        if (marks <= 0) {
            throw new QuestionException(ERROR_INVALID_MARKS);
        }
    }

    private boolean isValidDifficulty(String difficulty) {
        return difficulty != null && (difficulty.equalsIgnoreCase(DIFFICULTY_EASY) ||
                difficulty.equalsIgnoreCase(DIFFICULTY_MEDIUM) ||
                difficulty.equalsIgnoreCase(DIFFICULTY_HARD));
    }
}