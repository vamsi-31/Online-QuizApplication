package com.onlinequiz.services.impl;

import com.onlinequiz.dao.QuestionDAO;
import com.onlinequiz.exception.QuestionException;
import com.onlinequiz.models.Question;
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

    private QuestionServiceImpl questionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        questionService = new QuestionServiceImpl(questionDAO);
    }

    @Test
    void createQuestion_Success() {
        // Arrange
        String title = "Test Question";
        List<String> options = Arrays.asList("A", "B", "C");
        int correctOptionIndex = 1;
        String difficulty = "Easy";
        List<String> topics = Arrays.asList("Topic1");
        int marks = 2;

        when(questionDAO.createQuestion(any(Question.class))).thenAnswer(i -> {
            Question q = (Question) i.getArguments()[0];
            assertNotNull(q.getId());
            return q;
        });

        // Act
        Question result = questionService.createQuestion(title, options, correctOptionIndex, difficulty, topics, marks);

        // Assert
        assertNotNull(result.getId());
        assertEquals(title, result.getTitle());
        assertEquals(options, result.getOptions());
        assertEquals(correctOptionIndex, result.getCorrectOptionIndex());
        assertEquals(difficulty, result.getDifficulty());
        assertEquals(topics, result.getTopics());
        assertEquals(marks, result.getMarks());
        verify(questionDAO, times(1)).createQuestion(any(Question.class));
    }

    @Test
    void createQuestion_EmptyTitle_ThrowsException() {
        // Act & Assert
        assertThrows(QuestionException.class, () -> questionService.createQuestion("", Arrays.asList("A", "B"), 0, "Easy", Arrays.asList("Topic1"), 1));
    }

    @Test
    void createQuestion_InsufficientOptions_ThrowsException() {
        // Act & Assert
        assertThrows(QuestionException.class, () -> questionService.createQuestion("Test Question", Arrays.asList("A"), 0, "Easy", Arrays.asList("Topic1"), 1));
    }

    @Test
    void createQuestion_InvalidCorrectOptionIndex_ThrowsException() {
        // Act & Assert
        assertThrows(QuestionException.class, () -> questionService.createQuestion("Test Question", Arrays.asList("A", "B"), 2, "Easy", Arrays.asList("Topic1"), 1));
    }

    @Test
    void createQuestion_InvalidDifficulty_ThrowsException() {
        // Act & Assert
        assertThrows(QuestionException.class, () -> questionService.createQuestion("Test Question", Arrays.asList("A", "B", "C"), 1, "InvalidDifficulty", Arrays.asList("Topic1"), 2));
    }
    @Test
    void createQuestion_NullQuestion_ThrowsException() {
        // Act & Assert
        assertThrows(QuestionException.class, () -> questionService.createQuestion(null, null, 0, null, null, 0));
    }

    @Test
    void getQuestionById_Success() {
        // Arrange
        String id = "question1";
        Question expectedQuestion = new Question(id, "Test Question", Arrays.asList("A", "B"), 0, "Easy", Arrays.asList("Topic1"), 1);
        when(questionDAO.getQuestionById(id)).thenReturn(Optional.of(expectedQuestion));

        // Act
        Optional<Question> result = questionService.getQuestionById(id);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(expectedQuestion, result.get());
        verify(questionDAO, times(1)).getQuestionById(id);
    }

    @Test
    void getQuestionById_EmptyId_ThrowsException() {
        // Act & Assert
        assertThrows(QuestionException.class, () -> questionService.getQuestionById(""));
    }

    @Test
    void getAllQuestions_Success() {
        // Arrange
        List<Question> expectedQuestions = Arrays.asList(
                new Question("1", "Q1", Arrays.asList("A", "B"), 0, "Easy", Arrays.asList("Topic1"), 1),
                new Question("2", "Q2", Arrays.asList("A", "B", "C"), 1, "Medium", Arrays.asList("Topic2"), 2)
        );
        when(questionDAO.getAllQuestions()).thenReturn(expectedQuestions);

        // Act
        List<Question> result = questionService.getAllQuestions();

        // Assert
        assertEquals(expectedQuestions, result);
        verify(questionDAO, times(1)).getAllQuestions();
    }

    @Test
    void updateQuestion_Success() {
        // Arrange
        Question question = new Question("1", "Updated Question", Arrays.asList("A", "B", "C"), 2, "Hard", Arrays.asList("Topic3"), 3);
        when(questionDAO.updateQuestion(question)).thenReturn(question);

        // Act
        Question result = questionService.updateQuestion(question);

        // Assert
        assertEquals(question, result);
        verify(questionDAO, times(1)).updateQuestion(question);
    }

    @Test
    void updateQuestion_NullQuestion_ThrowsException() {
        // Act & Assert
        assertThrows(QuestionException.class, () -> questionService.updateQuestion(null));
    }

    @Test
    void updateQuestion_EmptyId_ThrowsException() {
        // Arrange
        Question question = new Question("", "Test Question", Arrays.asList("A", "B"), 0, "Easy", Arrays.asList("Topic1"), 1);

        // Act & Assert
        assertThrows(QuestionException.class, () -> questionService.updateQuestion(question));
    }

    @Test
    void updateQuestion_InvalidData_ThrowsException() {
        // Arrange
        Question question = new Question("1", "", Arrays.asList("A"), 0, "Easy", Arrays.asList("Topic1"), 1);

        // Act & Assert
        assertThrows(QuestionException.class, () -> questionService.updateQuestion(question));
    }

    @Test
    void deleteQuestion_Success() {
        // Arrange
        String id = "question1";
        when(questionDAO.isDeleteQuestion(id)).thenReturn(true);

        // Act
        boolean result = questionService.isDeleteQuestion(id);

        // Assert
        assertTrue(result);
        verify(questionDAO, times(1)).isDeleteQuestion(id);
    }

    @Test
    void deleteQuestion_EmptyId_ThrowsException() {
        // Act & Assert
        assertThrows(QuestionException.class, () -> questionService.isDeleteQuestion(""));
    }

    @Test
    void getQuestionById_QuestionNotFound_ReturnsEmptyOptional() {
        // Arrange
        String id = "nonexistent";
        when(questionDAO.getQuestionById(id)).thenReturn(Optional.empty());

        // Act
        Optional<Question> result = questionService.getQuestionById(id);

        // Assert
        assertFalse(result.isPresent());
        verify(questionDAO, times(1)).getQuestionById(id);
    }
}