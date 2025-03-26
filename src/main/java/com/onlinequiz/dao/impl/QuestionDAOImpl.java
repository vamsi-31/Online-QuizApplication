package com.onlinequiz.dao.impl;

import com.onlinequiz.dao.QuestionDAO;
import com.onlinequiz.models.Question;
import java.util.*;
import org.springframework.stereotype.Repository;
@Repository
public class QuestionDAOImpl implements QuestionDAO {
    private final Map<String, Question> questions = new HashMap<>();

    @Override
    public Question createQuestion(Question question) {
        questions.put(question.getId(), question);
        return question;
    }

    @Override
    public Optional<Question> getQuestionById(String id) {
        return Optional.ofNullable(questions.get(id));
    }

    @Override
    public List<Question> getAllQuestions() {
        return new ArrayList<>(questions.values());
    }

    @Override
    public Question updateQuestion(Question question) {
        questions.put(question.getId(), question);
        return question;
    }

    @Override
    public boolean isDeleteQuestion(String id) {
        return questions.remove(id) != null;
    }
}