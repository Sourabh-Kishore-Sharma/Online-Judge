package com.algojudge.algojudge.controller;

import com.algojudge.algojudge.entity.Problem;
import com.algojudge.algojudge.entity.Submission;
import com.algojudge.algojudge.entity.Testcase;
import com.algojudge.algojudge.entity.User;
import com.algojudge.algojudge.service.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/compiler")
public class CompilerController extends BaseController{

    private final CompilerService compilerService;
    private final TestcaseService testcaseService;
    private final SubmissionService submissionService;
    private final UserService userService;
    private final ProblemService problemService;

    public CompilerController(CompilerService compilerService, TestcaseService testcaseService, SubmissionService submissionService, UserService userService, ProblemService problemService) {
        this.compilerService = compilerService;
        this.testcaseService = testcaseService;
        this.submissionService = submissionService;
        this.userService = userService;
        this.problemService = problemService;
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
        String verdict = "Success";
        for(Testcase testcase : testcases){
            try{
                String result = compilerService.compilerAndRun(file, language, testcase.getInput());
                result = result.replace("\n","");
                if(!result.equals(testcase.getOutput())) {
                    verdict = "Fail";
                    break;
                }
            }
            catch (Exception e){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
            }
        }

        String code = "";
        try {
            code = new String(file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String username = gerUsernameFromJwt();
        User user = userService.findByEmail(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + username));

        Problem problem = problemService.getProblemById(problemId)
                .orElseThrow(() -> new EntityNotFoundException("Problem not found with id: " + problemId));

        Submission submission = new Submission();
        submission.setUser(user);
        submission.setProblem(problem);
        submission.setCode(code);
        submissionService.saveSubmission(submission);
        return ResponseEntity.ok().body(Map.of("verdict", verdict));
    }
}
