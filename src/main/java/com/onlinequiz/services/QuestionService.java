package com.onlinequiz.services;

import com.onlinequiz.models.Question;
import java.util.List;
import java.util.Optional;

public interface QuestionService {
    Question createQuestion(String title, List<String> options, int correctOptionIndex, String difficulty, List<String> topics, int marks);
    Optional<Question> getQuestionById(String id);
    List<Question> getAllQuestions();
    Question updateQuestion(Question question);
    boolean isDeleteQuestion(String id);
}