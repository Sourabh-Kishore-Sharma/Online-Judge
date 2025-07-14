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
    String constraints;
    String inputFormat;
    String outputFormat;
    String exampleInput;
    String exampleOutput;
    float timeLimit;
    float memoryList;
    String difficultyLevel;
    List<String> tags;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    User user;

    @OneToMany(mappedBy = "problem", cascade = CascadeType.ALL)
    Set<Testcase> testcases;

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
