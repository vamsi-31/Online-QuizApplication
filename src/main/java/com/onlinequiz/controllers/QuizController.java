package com.onlinequiz.controllers;

import com.onlinequiz.models.Quiz;
import com.onlinequiz.services.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {

    private final QuizService quizService;

    @Autowired
    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @PostMapping
    public ResponseEntity<Quiz> createQuiz(@RequestBody Map<String, Object> payload) {
        String title = (String) payload.get("title");
        List<String> questionIds = (List<String>) payload.get("questionIds");
        return new ResponseEntity<>(quizService.createQuiz(title, questionIds), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Quiz> getQuizById(@PathVariable String id) {
        return quizService.getQuizById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Quiz>> getAllQuizzes() {
        return ResponseEntity.ok(quizService.getAllQuizzes());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Quiz> updateQuiz(@PathVariable String id, @RequestBody Quiz quiz) {
        quiz.setId(id);
        return ResponseEntity.ok(quizService.updateQuiz(quiz));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuiz(@PathVariable String id) {
        if (quizService.deleteQuiz(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/access/{accessCode}")
    public ResponseEntity<Quiz> getQuizByAccessCode(@PathVariable String accessCode) {
        return quizService.getQuizByAccessCode(accessCode)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/lock")
    public ResponseEntity<Void> lockQuiz(@PathVariable String id) {
        quizService.lockQuiz(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{accessCode}/take")
    public ResponseEntity<Integer> takeQuiz(@PathVariable String accessCode, @RequestBody List<Integer> userAnswers) {
        int score = quizService.takeQuiz(accessCode, userAnswers);
        return ResponseEntity.ok(score);
    }
}