package com.algojudge.algojudge.controller;

import com.algojudge.algojudge.entity.Problem;
import com.algojudge.algojudge.entity.Submission;
import com.algojudge.algojudge.entity.User;
import com.algojudge.algojudge.exception.ResourceNotFoundException;
import com.algojudge.algojudge.service.ProblemService;
import com.algojudge.algojudge.service.SubmissionService;
import com.algojudge.algojudge.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/submissions")
public class SubmissionController extends BaseController {

    private final SubmissionService submissionService;
    private final UserService userService;
    private final ProblemService problemService;

    public SubmissionController(SubmissionService submissionService, UserService userService, ProblemService problemService) {
        this.submissionService = submissionService;
        this.userService = userService;
        this.problemService = problemService;
    }

    @GetMapping
    public ResponseEntity<List<Submission>> getUserSubmissions(@RequestParam int problemId){
        User user = userService.findByEmail(gerUsernameFromJwt()).orElseThrow(() -> new ResourceNotFoundException("User not found."));
        Problem problem = problemService.getProblemById(problemId).orElseThrow(() -> new ResourceNotFoundException("Problem not found."));
        List<Submission> submissions = submissionService.getSubmissionsByUserAndProblem(user, problem);
        return ResponseEntity.ok(submissions);
    }


}