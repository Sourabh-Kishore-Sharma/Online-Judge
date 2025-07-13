package com.algojudge.algojudge.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "problems")
public class Problem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String title;
    String description;
    String author;
    String constraints;
    String inputFormat;
    String outputFormat;
    String exampleInput;
    String exampleOutput;
    float timeLimit;
    float memoryList;
    String difficultyLevel;
    int authorId;
    List<String> tags;

    @OneToMany(mappedBy = "problem", cascade = CascadeType.ALL)
    Set<Testcase> testcases;

    LocalDateTime createdOn;
    LocalDateTime lastUpdatedOn;

    public Problem(String title, String description, String author, String constraint, LocalDateTime createdOn, LocalDateTime lastUpdatedOn) {
        this.title = title;
        this.description = description;
        this.author = author;
        this.constraints = constraint;
        this.createdOn = createdOn;
        this.lastUpdatedOn = lastUpdatedOn;
    }
}
