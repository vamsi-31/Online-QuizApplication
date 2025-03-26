package com.onlinequiz.dao;

import com.onlinequiz.models.Question;
import java.util.List;
import java.util.Optional;

public interface QuestionDAO {
    /**
     * Create a new question in the data store.
     *
     * @param question The question to be created.
     * @return The created question, potentially with a generated ID.
     */
    Question createQuestion(Question question);

    /**
     * Retrieve a question by its unique identifier.
     *
     * @param id The ID of the question to retrieve.
     * @return An Optional containing the question if found, or empty if not found.
     */
    Optional<Question> getQuestionById(String id);

    /**
     * Retrieve all questions from the data store.
     *
     * @return A list of all questions.
     */
    List<Question> getAllQuestions();

    /**
     * Update an existing question in the data store.
     *
     * @param question The question with updated information.
     * @return The updated question.
     */
    Question updateQuestion(Question question);

    /**
     * Delete a question from the data store by its ID.
     *
     * @param id The ID of the question to delete.
     * @return true if the question was successfully deleted, false otherwise.
     */
    boolean isDeleteQuestion(String id);
}