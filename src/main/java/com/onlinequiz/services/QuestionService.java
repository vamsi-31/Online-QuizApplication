package com.onlinequiz.services;

import com.onlinequiz.models.Question;
import java.util.List;
import java.util.Optional;

public interface QuestionService {
    Question createQuestion(Question question);
    Optional<Question> getQuestionById(String id);
    List<Question> getAllQuestions();
    Question updateQuestion(Question question);
    boolean deleteQuestion(String id);
    List<Question> getQuestionsByDifficulty(String difficulty);
    List<Question> getQuestionsByTopic(String topic);
}