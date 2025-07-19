package com.algojudge.algojudge.controller;

import com.algojudge.algojudge.entity.Problem;
import com.algojudge.algojudge.service.ProblemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/problems")
public class ProblemController {
    private final ProblemService problemService;

    public ProblemController(ProblemService problemService) {
        this.problemService = problemService;
    }

    // Create
    @PostMapping
    public ResponseEntity<Problem> createProblem(@RequestBody Problem problem) {
        Problem savedProblem = problemService.saveProblem(problem);
        return ResponseEntity.ok(savedProblem);
    }

    // Read All
    @GetMapping
    public ResponseEntity<List<Problem>> getAllProblems() {
        return ResponseEntity.ok(problemService.getAllProblems());
    }

    // Read by ID
    @GetMapping("/{id}")
    public ResponseEntity<Problem> getProblemById(@PathVariable int id) {
        return problemService.getProblemById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProblem(@PathVariable int id) {
        problemService.deleteProblemById(id);
        return ResponseEntity.noContent().build();
    }
}
