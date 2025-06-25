package com.onlinequiz.repositories;

import com.onlinequiz.models.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, String> {
    List<Question> findByDifficulty(String difficulty);
    List<Question> findByTopicsContaining(String topic);
}