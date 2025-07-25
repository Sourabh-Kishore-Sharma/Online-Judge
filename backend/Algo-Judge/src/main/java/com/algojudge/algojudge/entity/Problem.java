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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getConstraints() {
        return constraints;
    }

    public void setConstraints(String constraints) {
        this.constraints = constraints;
    }

    public String getInputFormat() {
        return inputFormat;
    }

    public void setInputFormat(String inputFormat) {
        this.inputFormat = inputFormat;
    }

    public String getOutputFormat() {
        return outputFormat;
    }

    public void setOutputFormat(String outputFormat) {
        this.outputFormat = outputFormat;
    }

    public String getExampleInput() {
        return exampleInput;
    }

    public void setExampleInput(String exampleInput) {
        this.exampleInput = exampleInput;
    }

    public String getExampleOutput() {
        return exampleOutput;
    }

    public void setExampleOutput(String exampleOutput) {
        this.exampleOutput = exampleOutput;
    }

    public float getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(float timeLimit) {
        this.timeLimit = timeLimit;
    }

    public float getMemoryList() {
        return memoryList;
    }

    public void setMemoryList(float memoryList) {
        this.memoryList = memoryList;
    }

    public String getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(String difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public Set<Testcase> getTestcases() {
        return testcases;
    }

    public void setTestcases(Set<Testcase> testcases) {
        this.testcases = testcases;
    }
}
