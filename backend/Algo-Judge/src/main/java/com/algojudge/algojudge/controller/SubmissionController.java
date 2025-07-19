package com.algojudge.algojudge.controller;

import com.algojudge.algojudge.entity.Submission;
import com.algojudge.algojudge.service.SubmissionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/submissions")
public class SubmissionController {

    private final SubmissionService submissionService;

    public SubmissionController(SubmissionService submissionService) {
        this.submissionService = submissionService;
    }

    // Create a new submission
    @PostMapping
    public ResponseEntity<Submission> createSubmission(@RequestBody Submission submission) {
        Submission savedSubmission = submissionService.saveSubmission(submission);
        return ResponseEntity.ok(savedSubmission);
    }

    // Get all submissions
    @GetMapping
    public ResponseEntity<List<Submission>> getAllSubmissions() {
        return ResponseEntity.ok(submissionService.getAllSubmissions());
    }

    // Get submission by ID
    @GetMapping("/{id}")
    public ResponseEntity<Submission> getSubmissionById(@PathVariable int id) {
        return submissionService.getSubmissionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete submission by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubmission(@PathVariable int id) {
        submissionService.deleteSubmissionById(id);
        return ResponseEntity.noContent().build();
    }
}