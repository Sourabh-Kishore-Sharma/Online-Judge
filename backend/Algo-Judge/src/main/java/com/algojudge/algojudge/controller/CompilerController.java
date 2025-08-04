package com.algojudge.algojudge.controller;

import com.algojudge.algojudge.entity.Testcase;
import com.algojudge.algojudge.service.CompilerService;
import com.algojudge.algojudge.service.TestcaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/compiler")
public class CompilerController {

    private final CompilerService compilerService;
    private final TestcaseService testcaseService;

    public CompilerController(CompilerService compilerService, TestcaseService testcaseService) {
        this.compilerService = compilerService;
        this.testcaseService = testcaseService;
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

    @PostMapping("/validate")
    public ResponseEntity<?> validate(@RequestParam("file") MultipartFile file,
                                      @RequestParam("language") String language,
                                      @RequestParam("problemId") int problemId){
        List<Testcase> testcases = testcaseService.getAllTestcases(problemId);
        for(Testcase testcase : testcases){
            try{
                String verdict = compilerService.compilerAndRun(file, language, testcase.getInput());
                verdict = verdict.replace("\n","");
                if(!verdict.equals(testcase.getOutput())) {
                    return ResponseEntity.ok().body(Map.of("verdict", "Fail"));
                }
            }
            catch (Exception e){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
            }
        }
        return ResponseEntity.ok().body(Map.of("verdict", "Success"));
    }
}
