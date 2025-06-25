package com.onlinequiz.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String title;

    @ElementCollection
    @CollectionTable(name = "question_options", joinColumns = @JoinColumn(name = "question_id"))
    @Column(name = "`option`")
    private List<String> options;

    private int correctOptionIndex;

    @Column(nullable = false)
    private String difficulty;

    @ElementCollection
    @CollectionTable(name = "question_topics", joinColumns = @JoinColumn(name = "question_id"))
    @Column(name = "topic")
    private List<String> topics;

    private int marks;
}