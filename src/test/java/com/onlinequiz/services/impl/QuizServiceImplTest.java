package com.onlinequiz.services.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.onlinequiz.dao.QuizDAO;
import com.onlinequiz.models.Quiz;
import com.onlinequiz.models.Question;
import com.onlinequiz.exception.QuizException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

class QuizServiceImplTest {

    @Mock
    private QuizDAO quizDAO;

    private QuizServiceImpl quizService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        quizService = new QuizServiceImpl(quizDAO);
    }

    @Test
    void createQuiz_ValidInput_Success() {
        String title = "Test Quiz";
        List<Question> questions = Arrays.asList(
                new Question("1", "Q1", Arrays.asList("A", "B"), 0, "EASY", Arrays.asList("Topic1"), 1),
                new Question("2", "Q2", Arrays.asList("A", "B"), 1, "MEDIUM", Arrays.asList("Topic2"), 2)
        );

        Quiz createdQuiz = new Quiz("quiz1", title, questions, 3, "ABC123", true);
        when(quizDAO.createQuiz(any(Quiz.class))).thenReturn(createdQuiz);

        Quiz result = quizService.createQuiz(title, questions);

        assertNotNull(result);
        assertEquals(title, result.getTitle());
        assertEquals(questions.size(), result.getQuestions().size());
        assertEquals(3, result.getTotalMarks());
        assertTrue(result.isModifiable());
        verify(quizDAO, times(1)).createQuiz(any(Quiz.class));
    }

    @Test
    void createQuiz_EmptyTitle_ThrowsException() {
        List<Question> questions = Arrays.asList(new Question("1", "Q1", Arrays.asList("A", "B"), 0, "EASY", Arrays.asList("Topic1"), 1));

        assertThrows(QuizException.class, () -> quizService.createQuiz("", questions));
    }

    @Test
    void getQuizById_ExistingId_ReturnsQuiz() {
        String quizId = "quiz1";
        Quiz expectedQuiz = new Quiz(quizId, "Test Quiz", Arrays.asList(), 0, "ABC123", true);
        when(quizDAO.getQuizById(quizId)).thenReturn(Optional.of(expectedQuiz));

        Optional<Quiz> result = quizService.getQuizById(quizId);

        assertTrue(result.isPresent());
        assertEquals(expectedQuiz, result.get());
    }

    @Test
    void updateQuiz_ModifiableQuiz_Success() {
        Quiz quiz = new Quiz("quiz1", "Test Quiz", Arrays.asList(), 0, "ABC123", true);
        when(quizDAO.updateQuiz(quiz)).thenReturn(quiz);

        Quiz result = quizService.updateQuiz(quiz);

        assertNotNull(result);
        assertEquals(quiz, result);
        verify(quizDAO, times(1)).updateQuiz(quiz);
    }

    @Test
    void updateQuiz_NonModifiableQuiz_ThrowsException() {
        Quiz quiz = new Quiz("quiz1", "Test Quiz", Arrays.asList(), 0, "ABC123", false);

        assertThrows(QuizException.class, () -> quizService.updateQuiz(quiz));
    }
}