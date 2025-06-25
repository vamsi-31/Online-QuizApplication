package com.onlinequiz.services;
import com.onlinequiz.models.Quiz;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
public interface QuizService {
    Quiz createQuiz(String title, List<String> questionIds);
    Optional<Quiz> getQuizById(String id);
    List<Quiz> getAllQuizzes();
    Quiz updateQuiz(Quiz quiz);
    boolean deleteQuiz(String id); // Changed from isDeleteQuiz
    Optional<Quiz> getQuizByAccessCode(String accessCode);
    void lockQuiz(String id);
    int takeQuiz(String accessCode, List<Integer> userAnswers); // Changed parameter
}