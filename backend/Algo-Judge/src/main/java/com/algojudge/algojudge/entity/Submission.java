package com.algojudge.algojudge.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "submissions")
public class Submission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int Id;
    int userId;

    LocalDateTime createdOn;
}
