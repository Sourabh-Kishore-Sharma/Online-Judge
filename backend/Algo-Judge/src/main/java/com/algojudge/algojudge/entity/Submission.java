package com.algojudge.algojudge.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "submissions")
public class Submission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int Id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @ManyToOne
    @JoinColumn(name = "problem_id", nullable = false)
    Problem problem;

    String code;
    String language;

    LocalDateTime submittedAt;
    String status;
    float executionTime;
    float memoryUsage;
    String output;
    String errorMessage;

    LocalDateTime createdOn;

    @PrePersist
    void onCreate(){
        this.createdOn = LocalDateTime.now();
    }
}
