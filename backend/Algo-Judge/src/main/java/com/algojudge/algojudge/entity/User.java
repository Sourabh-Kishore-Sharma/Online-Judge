package com.algojudge.algojudge.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String email;
    String password;

    String firstName;
    String middleName;
    String lastName;
    LocalDateTime createdOn;
    LocalDateTime lastUpdatedOn;

    public User(String firstName, String middleName, String lastName) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
    }
}
