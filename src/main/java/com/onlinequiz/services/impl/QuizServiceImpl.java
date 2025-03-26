package com.onlinequiz.services.impl;

import com.onlinequiz.dao.QuizDAO;
import com.onlinequiz.models.Quiz;
import com.onlinequiz.models.Question;
import com.onlinequiz.services.QuestionService;
import com.onlinequiz.services.QuizService;
import com.onlinequiz.exception.QuizException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.onlinequiz.constants.Constants.*;

@Service
public class QuizServiceImpl implements QuizService {
    private final QuizDAO quizDAO;
    private final QuestionService questionService;
    @Autowired
    public QuizServiceImpl(QuizDAO quizDAO, QuestionService questionService) {
        this.quizDAO = quizDAO;
        this.questionService = questionService;
    }

    @Override
    public Quiz createQuiz(String title, Scanner scanner) {
        List<Question> questions = new ArrayList<>();
        while (true) {
            System.out.print("Enter question ID to add to the quiz (or 'done' to finish): ");
            String questionId = scanner.nextLine();
            if (questionId.equalsIgnoreCase("done")) {
                break;
            }

            Optional<Question> questionOpt = questionService.getQuestionById(questionId);
            if (questionOpt.isPresent()) {
                questions.add(questionOpt.get());
            } else {
                System.out.println(ERROR_QUESTION_NOT_FOUND);
            }
        }

        int totalMarks = questions.stream().mapToInt(Question::getMarks).sum();
        String accessCode = generateAccessCode();
        Quiz quiz = new Quiz(UUID.randomUUID().toString(), title, questions, totalMarks, accessCode, true);
        return quizDAO.createQuiz(quiz);
    }


    @Override
    public Optional<Quiz> getQuizById(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new QuizException(ERROR_EMPTY_QUIZ_ID);
        }
        return quizDAO.getQuizById(id);
    }

    @Override
    public List<Quiz> getAllQuizzes() {
        return quizDAO.getAllQuizzes();
    }

    @Override
    public Quiz updateQuiz(Quiz quiz) {
        if (quiz == null) {
            throw new QuizException(ERROR_NULL_QUIZ_ID);
        }
        if (!quiz.isModifiable()) {
            throw new QuizException(ERROR_EMPTY_QUIZ_MODIFY);
        }
        return quizDAO.updateQuiz(quiz);
    }

    @Override
    public boolean isDeleteQuiz(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new QuizException(ERROR_EMPTY_QUIZ_ID);
        }
        return quizDAO.isDeleteQuiz(id);
    }

    @Override
    public Optional<Quiz> getQuizByAccessCode(String accessCode) {
        if (accessCode == null || accessCode.trim().isEmpty()) {
            throw new QuizException(ERROR_EMPTY_ACCESS_CODE);
        }
        return quizDAO.getQuizByAccessCode(accessCode);
    }

    @Override
    public void lockQuiz(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new QuizException(ERROR_EMPTY_QUIZ_ID);
        }
        Optional<Quiz> quizOpt = quizDAO.getQuizById(id);
        if (quizOpt.isPresent()) {
            Quiz quiz = quizOpt.get();
            quiz.setModifiable(false);
            quizDAO.updateQuiz(quiz);
        } else {
            throw new QuizException(ERROR_QUIZ_NOT_FOUND);
        }
    }

    @Override
    public int takeQuiz(String accessCode, Scanner scanner) {
        Optional<Quiz> quizOpt = getQuizByAccessCode(accessCode);
        if (quizOpt.isPresent()) {
            Quiz quiz = quizOpt.get();
            int score = 0;

            for (Question question : quiz.getQuestions()) {
                System.out.println(question.getTitle());
                List<String> options = question.getOptions();
                for (int i = 0; i < options.size(); i++) {
                    System.out.println((i + 1) + ". " + options.get(i));
                }

                System.out.print("Enter your answer (1-" + options.size() + "): ");
                int userAnswer = Integer.parseInt(scanner.nextLine()) - 1;

                if (userAnswer == question.getCorrectOptionIndex()) {
                    score += question.getMarks();
                }
            }

            return score;
        } else {
            throw new QuizException(ERROR_INVALID_ACCESS_CODE);
        }
    }

    private String generateAccessCode() {
        return UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    }
}
