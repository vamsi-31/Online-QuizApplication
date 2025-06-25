package com.onlinequiz.services.impl;

import com.onlinequiz.models.Question;
import com.onlinequiz.repositories.QuestionRepository;
import com.onlinequiz.services.QuestionService;
import com.onlinequiz.exception.QuestionException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.onlinequiz.constants.Constants.*;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;

    @Override
    @Transactional
    public Question createQuestion(Question question) {
        return Optional.ofNullable(question)
                .map(questionRepository::save)
                .orElseThrow(() -> new QuestionException("Invalid question"));
    }

    @Override
    public Optional<Question> getQuestionById(String id) {
        return questionRepository.findById(id);
    }

    @Override
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    @Override
    @Transactional
    public Question updateQuestion(Question question) {
        return Optional.ofNullable(question)
                .filter(q -> questionRepository.existsById(q.getId()))
                .map(questionRepository::save)
                .orElseThrow(() -> new QuestionException(ERROR_QUESTION_NOT_FOUND));
    }

    @Override
    @Transactional
    public boolean deleteQuestion(String id) {
        return questionRepository.findById(id)
                .map(question -> {
                    questionRepository.delete(question);
                    return true;
                })
                .orElse(false);
    }

    @Override
    public List<Question> getQuestionsByDifficulty(String difficulty) {
        return questionRepository.findByDifficulty(difficulty);
    }

    @Override
    public List<Question> getQuestionsByTopic(String topic) {
        return questionRepository.findByTopicsContaining(topic);
    }
}