package com.onlinequiz.services;

import com.onlinequiz.models.Quiz;
import com.onlinequiz.models.Question;
import java.util.List;
import java.util.Optional;

public interface QuizService {
    Quiz createQuiz(String title, List<Question> questions);
    Optional<Quiz> getQuizById(String id);
    List<Quiz> getAllQuizzes();
    Quiz updateQuiz(Quiz quiz);
    boolean deleteQuiz(String id);
    Optional<Quiz> getQuizByAccessCode(String accessCode);
    void lockQuiz(String id);
}