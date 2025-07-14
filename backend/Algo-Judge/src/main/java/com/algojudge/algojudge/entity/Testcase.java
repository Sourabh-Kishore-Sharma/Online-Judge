package com.algojudge.algojudge.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "testcases")
public class Testcase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String input;
    String output;
    boolean isSample;

    @ManyToOne
    @JoinColumn(name = "problem_id", nullable = false)
    Problem problem;

    LocalDateTime createdOn;
    LocalDateTime lastUpdatedOn;

    @PrePersist
    void onCreate(){
        this.createdOn = LocalDateTime.now();
        this.lastUpdatedOn = LocalDateTime.now();
    }

    @PreUpdate
    void onUpdate(){
        this.lastUpdatedOn = LocalDateTime.now();
    }

}
