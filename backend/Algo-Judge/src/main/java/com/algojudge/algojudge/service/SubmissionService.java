package com.algojudge.algojudge.service;

import com.algojudge.algojudge.entity.Problem;
import com.algojudge.algojudge.entity.Submission;
import com.algojudge.algojudge.entity.User;
import com.algojudge.algojudge.repository.SubmissionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SubmissionService {
    private final SubmissionRepository submissionRepository;

    public SubmissionService(SubmissionRepository submissionRepository) {
        this.submissionRepository = submissionRepository;
    }

    public List<Submission> getSubmissionsByUserAndProblem(User user, Problem problem){
        return submissionRepository.findByUserAndProblemOrderByCreatedOnDesc(user, problem);
    }

    public Submission saveSubmission(Submission submission){
        return submissionRepository.save(submission);
    }
}
