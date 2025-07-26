package com.algojudge.algojudge.controller;

import com.algojudge.algojudge.entity.Testcase;
import com.algojudge.algojudge.service.TestcaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/testcases")
public class TestcaseController {
    private final TestcaseService testcaseService;

    public TestcaseController(TestcaseService testcaseService) {
        this.testcaseService = testcaseService;
    }

    // Create
    @PostMapping
    public ResponseEntity<Testcase> createTestcase(@RequestBody Testcase testcase) {
        if(testcase.){

        }
        Testcase savedTestcase = testcaseService.saveTestcase(testcase);
        return ResponseEntity.ok(savedTestcase);
    }

    // Read All
    @GetMapping
    public ResponseEntity<List<Testcase>> getAllTestcases() {
        return ResponseEntity.ok(testcaseService.getAllTestcases());
    }

    // Read by ID
    @GetMapping("/{id}")
    public ResponseEntity<Testcase> getTestcaseById(@PathVariable int id) {
        return testcaseService.getTestcaseById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTestcase(@PathVariable int id) {
        testcaseService.deleteTestcaseById(id);
        return ResponseEntity.noContent().build();
    }
}
