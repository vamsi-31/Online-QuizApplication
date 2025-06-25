package com.onlinequiz.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a quiz in the application.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String title;

    @ManyToMany
    @JoinTable(
            name = "quiz_questions",
            joinColumns = @JoinColumn(name = "quiz_id"),
            inverseJoinColumns = @JoinColumn(name = "question_id")
    )
    @Builder.Default  // This annotation ensures the list is initialized even when using the builder
    private List<Question> questions = new ArrayList<>();

    private int totalMarks;

    @Column(unique = true)
    private String accessCode;

    private boolean modifiable;
}