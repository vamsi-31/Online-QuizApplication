package com.onlinequiz.dao;

import com.onlinequiz.models.Quiz;
import java.util.List;
import java.util.Optional;

/**
 * Data Access Object interface for Quiz-related operations.
 */
public interface QuizDAO {
    /**
     * Create a new quiz.
     *
     * @param quiz The quiz to be created.
     * @return The created quiz with generated ID.
     */
    Quiz createQuiz(Quiz quiz);

    /**
     * Retrieve a quiz by its ID.
     *
     * @param id The ID of the quiz to retrieve.
     * @return An Optional containing the quiz if found, or empty if not found.
     */
    Optional<Quiz> getQuizById(String id);

    /**
     * Retrieve all quizzes.
     *
     * @return A list of all quizzes.
     */
    List<Quiz> getAllQuizzes();

    /**
     * Update an existing quiz.
     *
     * @param quiz The quiz with updated information.
     * @return The updated quiz.
     */
    Quiz updateQuiz(Quiz quiz);

    /**
     * Delete a quiz by its ID.
     *
     * @param id The ID of the quiz to delete.
     * @return true if the quiz was successfully deleted, false otherwise.
     */
    boolean isDeleteQuiz(String id);

    /**
     * Retrieve a quiz by its access code.
     *
     * @param accessCode The access code of the quiz.
     * @return An Optional containing the quiz if found, or empty if not found.
     */
    Optional<Quiz> getQuizByAccessCode(String accessCode);
}