package com.algojudge.algojudge.controller;

import com.algojudge.algojudge.service.CompilerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/compiler")
public class CompilerController {

    private final CompilerService compilerService;

    public CompilerController(CompilerService compilerService) {
        this.compilerService = compilerService;
    }

    @PostMapping("/submit")
    public ResponseEntity<?> compileAndRun(@RequestParam("file")MultipartFile file,
                                           @RequestParam("language") String language,
                                           @RequestParam("input") String input){
        try{
            String verdict = compilerService.compilerAndRun(file, language, input);
            return ResponseEntity.ok().body(Map.of("verdict",verdict));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
