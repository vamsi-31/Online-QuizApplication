package com.onlinequiz.services.impl;

import com.onlinequiz.dao.QuizDAO;
import com.onlinequiz.models.Quiz;
import com.onlinequiz.models.Question;
import com.onlinequiz.services.QuestionService;
import com.onlinequiz.exception.QuizException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class QuizServiceImplTest {

    @Mock
    private QuizDAO quizDAO;

    @Mock
    private QuestionService questionService;

    private QuizServiceImpl quizService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        quizService = new QuizServiceImpl(quizDAO, questionService);
    }

    @Test
    void createQuiz_Success() {
        // Arrange
        String title = "Test Quiz";
        Scanner scanner = new Scanner("1\n2\ndone\n");
        Question q1 = new Question("1", "Q1", Arrays.asList("A", "B"), 0, "Easy", Arrays.asList("Topic1"), 1);
        Question q2 = new Question("2", "Q2", Arrays.asList("A", "B"), 1, "Medium", Arrays.asList("Topic2"), 2);

        when(questionService.getQuestionById("1")).thenReturn(Optional.of(q1));
        when(questionService.getQuestionById("2")).thenReturn(Optional.of(q2));
        when(quizDAO.createQuiz(any(Quiz.class))).thenAnswer(i -> i.getArguments()[0]);

        // Act
        Quiz result = quizService.createQuiz(title, scanner);

        // Assert
        assertNotNull(result);
        assertEquals(title, result.getTitle());
        assertEquals(2, result.getQuestions().size());
        assertEquals(3, result.getTotalMarks());
        assertTrue(result.isModifiable());
        assertNotNull(result.getAccessCode());
        verify(quizDAO, times(1)).createQuiz(any(Quiz.class));
    }

    @Test
    void getQuizById_Success() {
        // Arrange
        String id = "quiz1";
        Quiz expectedQuiz = new Quiz(id, "Test Quiz", new ArrayList<>(), 10, "ACCESS", true);
        when(quizDAO.getQuizById(id)).thenReturn(Optional.of(expectedQuiz));

        // Act
        Optional<Quiz> result = quizService.getQuizById(id);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(expectedQuiz, result.get());
        verify(quizDAO, times(1)).getQuizById(id);
    }

    @Test
    void getQuizById_EmptyId_ThrowsException() {
        // Act & Assert
        assertThrows(QuizException.class, () -> quizService.getQuizById(""));
    }

    @Test
    void getAllQuizzes_Success() {
        // Arrange
        List<Quiz> expectedQuizzes = Arrays.asList(
                new Quiz("1", "Quiz 1", new ArrayList<>(), 10, "ACCESS1", true),
                new Quiz("2", "Quiz 2", new ArrayList<>(), 20, "ACCESS2", false)
        );
        when(quizDAO.getAllQuizzes()).thenReturn(expectedQuizzes);

        // Act
        List<Quiz> result = quizService.getAllQuizzes();

        // Assert
        assertEquals(expectedQuizzes, result);
        verify(quizDAO, times(1)).getAllQuizzes();
    }

    @Test
    void updateQuiz_Success() {
        // Arrange
        Quiz quiz = new Quiz("1", "Updated Quiz", new ArrayList<>(), 15, "ACCESS", true);
        when(quizDAO.updateQuiz(quiz)).thenReturn(quiz);

        // Act
        Quiz result = quizService.updateQuiz(quiz);

        // Assert
        assertEquals(quiz, result);
        verify(quizDAO, times(1)).updateQuiz(quiz);
    }

    @Test
    void updateQuiz_NullQuiz_ThrowsException() {
        // Act & Assert
        assertThrows(QuizException.class, () -> quizService.updateQuiz(null));
    }

    @Test
    void updateQuiz_NotModifiable_ThrowsException() {
        // Arrange
        Quiz quiz = new Quiz("1", "Quiz", new ArrayList<>(), 10, "ACCESS", false);

        // Act & Assert
        assertThrows(QuizException.class, () -> quizService.updateQuiz(quiz));
    }

    @Test
    void deleteQuiz_Success() {
        // Arrange
        String id = "quiz1";
        when(quizDAO.isDeleteQuiz(id)).thenReturn(true);

        // Act
        boolean result = quizService.isDeleteQuiz(id);

        // Assert
        assertTrue(result);
        verify(quizDAO, times(1)).isDeleteQuiz(id);
    }

    @Test
    void deleteQuiz_EmptyId_ThrowsException() {
        // Act & Assert
        assertThrows(QuizException.class, () -> quizService.isDeleteQuiz(""));
    }

    @Test
    void getQuizByAccessCode_Success() {
        // Arrange
        String accessCode = "ACCESS";
        Quiz expectedQuiz = new Quiz("1", "Test Quiz", new ArrayList<>(), 10, accessCode, true);
        when(quizDAO.getQuizByAccessCode(accessCode)).thenReturn(Optional.of(expectedQuiz));

        // Act
        Optional<Quiz> result = quizService.getQuizByAccessCode(accessCode);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(expectedQuiz, result.get());
        verify(quizDAO, times(1)).getQuizByAccessCode(accessCode);
    }

    @Test
    void getQuizByAccessCode_EmptyAccessCode_ThrowsException() {
        // Act & Assert
        assertThrows(QuizException.class, () -> quizService.getQuizByAccessCode(""));
    }

    @Test
    void lockQuiz_Success() {
        // Arrange
        String id = "quiz1";
        Quiz quiz = new Quiz(id, "Test Quiz", new ArrayList<>(), 10, "ACCESS", true);
        when(quizDAO.getQuizById(id)).thenReturn(Optional.of(quiz));

        // Act
        quizService.lockQuiz(id);

        // Assert
        assertFalse(quiz.isModifiable());
        verify(quizDAO, times(1)).updateQuiz(quiz);
    }

    @Test
    void lockQuiz_QuizNotFound_ThrowsException() {
        // Arrange
        String id = "quiz1";
        when(quizDAO.getQuizById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(QuizException.class, () -> quizService.lockQuiz(id));
    }

    @Test
    void takeQuiz_Success() {
        // Arrange
        String accessCode = "ACCESS";
        Quiz quiz = new Quiz("1", "Test Quiz", Arrays.asList(
                new Question("q1", "Q1", Arrays.asList("A", "B"), 0, "Easy", Arrays.asList("Topic1"), 1),
                new Question("q2", "Q2", Arrays.asList("A", "B", "C"), 2, "Medium", Arrays.asList("Topic2"), 2)
        ), 3, accessCode, false);
        when(quizDAO.getQuizByAccessCode(accessCode)).thenReturn(Optional.of(quiz));
        Scanner scanner = new Scanner("1\n3\n");

        // Act
        int score = quizService.takeQuiz(accessCode, scanner);

        // Assert
        assertEquals(3, score);
    }

    @Test
    void takeQuiz_QuizNotFound_ThrowsException() {
        // Arrange
        String accessCode = "INVALID";
        when(quizDAO.getQuizByAccessCode(accessCode)).thenReturn(Optional.empty());
        Scanner scanner = new Scanner("");

        // Act & Assert
        assertThrows(QuizException.class, () -> quizService.takeQuiz(accessCode, scanner));
    }
}