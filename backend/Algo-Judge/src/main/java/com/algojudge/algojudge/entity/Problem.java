package com.algojudge.algojudge.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "problems")
public class Problem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String title;
    String description;
    String author;
    String constraint;

    LocalDateTime createdOn;
    LocalDateTime lastUpdatedOn;

    public Problem(String title, String description, String author, String constraint, LocalDateTime createdOn, LocalDateTime lastUpdatedOn) {
        this.title = title;
        this.description = description;
        this.author = author;
        this.constraint = constraint;
        this.createdOn = createdOn;
        this.lastUpdatedOn = lastUpdatedOn;
    }
}
