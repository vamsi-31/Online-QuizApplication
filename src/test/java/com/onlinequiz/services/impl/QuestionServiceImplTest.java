package com.onlinequiz.services.impl;

import com.onlinequiz.dao.QuestionDAO;
import com.onlinequiz.models.Question;
import com.onlinequiz.services.QuestionService;
import com.onlinequiz.exception.QuestionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class QuestionServiceImplTest {

    @Mock
    private QuestionDAO questionDAO;

    private QuestionService questionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        questionService = new QuestionServiceImpl(questionDAO);
    }

    @Test
    void testCreateQuestion() {
        Question question = new Question(null, "Test Question", Arrays.asList("A", "B"), 0, "EASY", Arrays.asList("Test"), 1);
        Question expectedQuestion = new Question("1", "Test Question", Arrays.asList("A", "B"), 0, "EASY", Arrays.asList("Test"), 1);

        when(questionDAO.createQuestion(any(Question.class))).thenReturn(expectedQuestion);

        Question createdQuestion = questionService.createQuestion(question);

        assertNotNull(createdQuestion);
        assertEquals("1", createdQuestion.getId());
        verify(questionDAO, times(1)).createQuestion(any(Question.class));
    }

    @Test
    void testCreateQuestionWithInvalidData() {
        Question invalidQuestion = new Question(null, "", Arrays.asList("A"), 0, "EASY", Arrays.asList("Test"), 1);

        assertThrows(QuestionException.class, () -> questionService.createQuestion(invalidQuestion));
    }

    @Test
    void testGetQuestionById() {
        String questionId = "1";
        Question expectedQuestion = new Question(questionId, "Test Question", Arrays.asList("A", "B"), 0, "EASY", Arrays.asList("Test"), 1);

        when(questionDAO.getQuestionById(questionId)).thenReturn(Optional.of(expectedQuestion));

        Optional<Question> retrievedQuestion = questionService.getQuestionById(questionId);

        assertTrue(retrievedQuestion.isPresent());
        assertEquals(questionId, retrievedQuestion.get().getId());
        verify(questionDAO, times(1)).getQuestionById(questionId);
    }

    @Test
    void testGetAllQuestions() {
        List<Question> expectedQuestions = Arrays.asList(
                new Question("1", "Question 1", Arrays.asList("A", "B"), 0, "EASY", Arrays.asList("Test"), 1),
                new Question("2", "Question 2", Arrays.asList("A", "B", "C"), 1, "MEDIUM", Arrays.asList("Test"), 2)
        );

        when(questionDAO.getAllQuestions()).thenReturn(expectedQuestions);

        List<Question> retrievedQuestions = questionService.getAllQuestions();

        assertEquals(2, retrievedQuestions.size());
        verify(questionDAO, times(1)).getAllQuestions();
    }

    @Test
    void testUpdateQuestion() {
        Question questionToUpdate = new Question("1", "Updated Question", Arrays.asList("A", "B", "C"), 1, "MEDIUM", Arrays.asList("Test"), 2);

        when(questionDAO.updateQuestion(questionToUpdate)).thenReturn(questionToUpdate);

        Question updatedQuestion = questionService.updateQuestion(questionToUpdate);

        assertEquals("Updated Question", updatedQuestion.getTitle());
        assertEquals("MEDIUM", updatedQuestion.getDifficulty());
        verify(questionDAO, times(1)).updateQuestion(questionToUpdate);
    }

    @Test
    void testDeleteQuestion() {
        String questionId = "1";

        when(questionDAO.deleteQuestion(questionId)).thenReturn(true);

        boolean deleted = questionService.deleteQuestion(questionId);

        assertTrue(deleted);
        verify(questionDAO, times(1)).deleteQuestion(questionId);
    }
}