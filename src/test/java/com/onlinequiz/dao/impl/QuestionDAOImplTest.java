package com.onlinequiz.dao.impl;

import com.onlinequiz.dao.QuestionDAO;
import com.onlinequiz.models.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class QuestionDAOImplTest {

    private QuestionDAO questionDAO;

    @BeforeEach
    void setUp() {
        questionDAO = new QuestionDAOImpl();
    }

    @Test
    void testCreateQuestion() {
        Question question = new Question("1", "Test Question", Arrays.asList("A", "B"), 0, "EASY", Arrays.asList("Test"), 1);
        Question createdQuestion = questionDAO.createQuestion(question);
        assertNotNull(createdQuestion);
        assertEquals("1", createdQuestion.getId());
    }

    @Test
    void testGetQuestionById() {
        Question question = new Question("1", "Test Question", Arrays.asList("A", "B"), 0, "EASY", Arrays.asList("Test"), 1);
        questionDAO.createQuestion(question);
        Optional<Question> retrievedQuestion = questionDAO.getQuestionById("1");
        assertTrue(retrievedQuestion.isPresent());
        assertEquals("Test Question", retrievedQuestion.get().getTitle());
    }

    @Test
    void testGetAllQuestions() {
        Question question1 = new Question("1", "Test Question 1", Arrays.asList("A", "B"), 0, "EASY", Arrays.asList("Test"), 1);
        Question question2 = new Question("2", "Test Question 2", Arrays.asList("A", "B"), 1, "MEDIUM", Arrays.asList("Test"), 2);
        questionDAO.createQuestion(question1);
        questionDAO.createQuestion(question2);
        List<Question> questions = questionDAO.getAllQuestions();
        assertEquals(2, questions.size());
    }

    @Test
    void testUpdateQuestion() {
        Question question = new Question("1", "Test Question", Arrays.asList("A", "B"), 0, "EASY", Arrays.asList("Test"), 1);
        questionDAO.createQuestion(question);
        question.setTitle("Updated Test Question");
        Question updatedQuestion = questionDAO.updateQuestion(question);
        assertEquals("Updated Test Question", updatedQuestion.getTitle());
    }

    @Test
    void testDeleteQuestion() {
        Question question = new Question("1", "Test Question", Arrays.asList("A", "B"), 0, "EASY", Arrays.asList("Test"), 1);
        questionDAO.createQuestion(question);
        boolean deleted = questionDAO.isDeleteQuestion("1");
        assertTrue(deleted);
        Optional<Question> deletedQuestion = questionDAO.getQuestionById("1");
        assertFalse(deletedQuestion.isPresent());
    }
}