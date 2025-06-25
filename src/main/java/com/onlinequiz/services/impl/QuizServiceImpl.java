package com.onlinequiz.services.impl;

import com.onlinequiz.models.Quiz;
import com.onlinequiz.models.Question;
import com.onlinequiz.repositories.QuizRepository;
import com.onlinequiz.repositories.QuestionRepository;
import com.onlinequiz.services.QuizService;
import com.onlinequiz.exception.QuizException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.onlinequiz.constants.Constants.*;

@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {

    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;

    @Override
    @Transactional
    public Quiz createQuiz(String title, List<String> questionIds) {
        List<Question> questions = questionRepository.findAllById(questionIds);
        if (questions.isEmpty()) {
            throw new QuizException("No valid questions found for the given IDs");
        }
        if (questions.size() != questionIds.size()) {
            throw new QuizException("Some questions were not found");
        }

        int totalMarks = questions.stream().mapToInt(Question::getMarks).sum();
        String accessCode = generateAccessCode();

        Quiz quiz = Quiz.builder()
                .title(title)
                .questions(questions)
                .totalMarks(totalMarks)
                .accessCode(accessCode)
                .modifiable(true)
                .build();

        return quizRepository.save(quiz);
    }

    @Override
    public Optional<Quiz> getQuizById(String id) {
        return quizRepository.findById(id);
    }

    @Override
    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }

    @Override
    @Transactional
    public Quiz updateQuiz(Quiz quiz) {
        return Optional.ofNullable(quiz)
                .filter(Quiz::isModifiable)
                .map(quizRepository::save)
                .orElseThrow(() -> new QuizException(ERROR_EMPTY_QUIZ_MODIFY));
    }


    @Override
    @Transactional
    public boolean deleteQuiz(String id) {
        return quizRepository.findById(id)
                .map(quiz -> {
                    quizRepository.delete(quiz);
                    return true;
                })
                .orElse(false);
    }

    @Override
    public Optional<Quiz> getQuizByAccessCode(String accessCode) {
        return quizRepository.findByAccessCode(accessCode);
    }

    @Override
    @Transactional
    public void lockQuiz(String id) {
        quizRepository.findById(id)
                .ifPresent(quiz -> {
                    quiz.setModifiable(false);
                    quizRepository.save(quiz);
                });
    }

    @Override
    public int takeQuiz(String accessCode, List<Integer> userAnswers) {
        return quizRepository.findByAccessCode(accessCode)
                .map(quiz -> {
                    if (userAnswers.size() != quiz.getQuestions().size()) {
                        throw new QuizException("Number of answers does not match number of questions");
                    }
                    return quiz.getQuestions().stream()
                            .filter(question -> userAnswers.get(quiz.getQuestions().indexOf(question)) == question.getCorrectOptionIndex())
                            .mapToInt(Question::getMarks)
                            .sum();
                })
                .orElseThrow(() -> new QuizException(ERROR_QUIZ_NOT_FOUND));
    }

    private String generateAccessCode() {
        return UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    }
}