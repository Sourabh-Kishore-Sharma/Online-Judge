package com.algojudge.algojudge.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "testcases")
public class Testcase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    int input;
    int output;
    boolean isSample;

    @ManyToOne
    @JoinColumn(name = "problem_id", nullable = false)
    Problem problem;

    LocalDateTime createdOn;
    LocalDateTime lastUpdatedOn;


}
