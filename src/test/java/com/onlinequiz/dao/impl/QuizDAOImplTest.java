//package com.onlinequiz.dao.impl;
//
//import com.onlinequiz.dao.QuizDAO;
//import com.onlinequiz.models.Quiz;
//import com.onlinequiz.models.Question;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class QuizDAOImplTest {
//
//    private QuizDAO quizDAO;
//
//    @BeforeEach
//    void setUp() {
//        quizDAO = new QuizDAOImpl();
//    }
//
//    @Test
//    void testCreateQuiz() {
//        Quiz quiz = new Quiz("1", "Test Quiz", Arrays.asList(new Question("1", "Test Question", Arrays.asList("A", "B"), 0, "EASY", Arrays.asList("Test"), 1)), 1, "ABC123", true);
//        Quiz createdQuiz = quizDAO.createQuiz(quiz);
//        assertNotNull(createdQuiz);
//        assertEquals("1", createdQuiz.getId());
//    }
//
//    @Test
//    void testGetQuizById() {
//        Quiz quiz = new Quiz("1", "Test Quiz", Arrays.asList(new Question("1", "Test Question", Arrays.asList("A", "B"), 0, "EASY", Arrays.asList("Test"), 1)), 1, "ABC123", true);
//        quizDAO.createQuiz(quiz);
//        Optional<Quiz> retrievedQuiz = quizDAO.getQuizById("1");
//        assertTrue(retrievedQuiz.isPresent());
//        assertEquals("Test Quiz", retrievedQuiz.get().getTitle());
//    }
//
//    @Test
//    void testGetAllQuizzes() {
//        Quiz quiz1 = new Quiz("1", "Test Quiz 1", Arrays.asList(new Question("1", "Test Question", Arrays.asList("A", "B"), 0, "EASY", Arrays.asList("Test"), 1)), 1, "ABC123", true);
//        Quiz quiz2 = new Quiz("2", "Test Quiz 2", Arrays.asList(new Question("2", "Test Question", Arrays.asList("A", "B"), 0, "EASY", Arrays.asList("Test"), 1)), 1, "DEF456", true);
//        quizDAO.createQuiz(quiz1);
//        quizDAO.createQuiz(quiz2);
//        List<Quiz> quizzes = quizDAO.getAllQuizzes();
//        assertEquals(2, quizzes.size());
//    }
//
//    @Test
//    void testUpdateQuiz() {
//        Quiz quiz = new Quiz("1", "Test Quiz", Arrays.asList(new Question("1", "Test Question", Arrays.asList("A", "B"), 0, "EASY", Arrays.asList("Test"), 1)), 1, "ABC123", true);
//        quizDAO.createQuiz(quiz);
//        quiz.setTitle("Updated Test Quiz");
//        Quiz updatedQuiz = quizDAO.updateQuiz(quiz);
//        assertEquals("Updated Test Quiz", updatedQuiz.getTitle());
//    }
//
//    @Test
//    void testDeleteQuiz() {
//        Quiz quiz = new Quiz("1", "Test Quiz", Arrays.asList(new Question("1", "Test Question", Arrays.asList("A", "B"), 0, "EASY", Arrays.asList("Test"), 1)), 1, "ABC123", true);
//        quizDAO.createQuiz(quiz);
//        boolean deleted = quizDAO.isDeleteQuiz("1");
//        assertTrue(deleted);
//        Optional<Quiz> deletedQuiz = quizDAO.getQuizById("1");
//        assertFalse(deletedQuiz.isPresent());
//    }
//
//    @Test
//    void testGetQuizByAccessCode() {
//        Quiz quiz = new Quiz("1", "Test Quiz", Arrays.asList(new Question("1", "Test Question", Arrays.asList("A", "B"), 0, "EASY", Arrays.asList("Test"), 1)), 1, "ABC123", true);
//        quizDAO.createQuiz(quiz);
//        Optional<Quiz> retrievedQuiz = quizDAO.getQuizByAccessCode("ABC123");
//        assertTrue(retrievedQuiz.isPresent());
//        assertEquals("Test Quiz", retrievedQuiz.get().getTitle());
//    }
//}