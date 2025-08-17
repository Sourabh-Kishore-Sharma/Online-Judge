package com.algojudge.algojudge.controller;

import com.algojudge.algojudge.entity.Problem;
import com.algojudge.algojudge.exception.ResourceNotFoundException;
import com.algojudge.algojudge.service.PerplexityService;
import com.algojudge.algojudge.service.ProblemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/ai")
public class PerplexityController {
    private final PerplexityService perplexityService;
    private final ProblemService problemService;

    public PerplexityController(PerplexityService perplexityService, ProblemService problemService) {
        this.perplexityService = perplexityService;
        this.problemService = problemService;
    }

    @PostMapping("/ask")
    public ResponseEntity<Map<String,String>> ask(@RequestBody Map<String,Object> request){
        String query = (String) request.get("query");
        int problemId = (Integer) request.get("problemId");

        Problem problem = problemService.getProblemById(problemId)
                .orElseThrow(() -> new ResourceNotFoundException("Problem not found"));
        String ans = perplexityService.getAnswer(query, problem.toString());
        return ResponseEntity.ok(Map.of("answer",ans));
    }
}
