package com.algojudge.algojudge.controller;

import com.algojudge.algojudge.service.PerplexityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/ai")
public class PerplexityController {
    private final PerplexityService perplexityService;

    public PerplexityController(PerplexityService perplexityService) {
        this.perplexityService = perplexityService;
    }

    @GetMapping("/ask")
    public ResponseEntity<Map<String,String>> ask(@RequestBody String query){
        String ans = perplexityService.getAnswer(query);
        return ResponseEntity.ok(Map.of("answer",ans));
    }
}
