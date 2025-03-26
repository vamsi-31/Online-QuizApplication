package com.onlinequiz.dao.impl;

import com.onlinequiz.dao.QuizDAO;
import com.onlinequiz.models.Quiz;
import java.util.*;
import org.springframework.stereotype.Repository;
@Repository
public class QuizDAOImpl implements QuizDAO {
    private final Map<String, Quiz> quizzes = new HashMap<>();

    @Override
    public Quiz createQuiz(Quiz quiz) {
        quizzes.put(quiz.getId(), quiz);
        return quiz;
    }

    @Override
    public Optional<Quiz> getQuizById(String id) {
        return Optional.ofNullable(quizzes.get(id));
    }

    @Override
    public List<Quiz> getAllQuizzes() {
        return new ArrayList<>(quizzes.values());
    }

    @Override
    public Quiz updateQuiz(Quiz quiz) {
        quizzes.put(quiz.getId(), quiz);
        return quiz;
    }

    @Override
    public boolean isDeleteQuiz(String id) {
        return quizzes.remove(id) != null;
    }

    @Override
    public Optional<Quiz> getQuizByAccessCode(String accessCode) {
        return quizzes.values().stream()
                .filter(quiz -> quiz.getAccessCode().equals(accessCode))
                .findFirst();
    }
}