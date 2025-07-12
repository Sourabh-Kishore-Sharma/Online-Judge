package com.algojudge.algojudge.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "testcases")
public class Testcase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    int questionId;
    int input;
    int output;


}
