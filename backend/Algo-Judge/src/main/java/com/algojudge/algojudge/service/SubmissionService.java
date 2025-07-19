package com.algojudge.algojudge.service;

import com.algojudge.algojudge.entity.Submission;
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

    public Submission saveSubmission(Submission submission){
        return this.submissionRepository.save(submission);
    }

    public Optional<Submission> getSubmissionById(int id){
        return this.submissionRepository.findById(id);
    }

    public List<Submission> getAllSubmissions(){
        return this.submissionRepository.findAll();
    }

    public void deleteSubmissionById(int id){
        this.submissionRepository.deleteById(id);
    }
}
