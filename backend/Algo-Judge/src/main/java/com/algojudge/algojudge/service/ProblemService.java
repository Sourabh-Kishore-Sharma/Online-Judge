package com.algojudge.algojudge.service;

import com.algojudge.algojudge.entity.Problem;
import com.algojudge.algojudge.repository.ProblemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProblemService {
    private final ProblemRepository problemRepository;

    public ProblemService(ProblemRepository problemRepository) {
        this.problemRepository = problemRepository;
    }

    public Problem saveProblem(Problem Problem){
        return this.problemRepository.save(Problem);
    }

    public Optional<Problem> getProblemById(int id){
        return this.problemRepository.findById(id);
    }

    public List<Problem> getAllProblems(){
        return this.problemRepository.findAll();
    }

    public void deleteProblemById(int id){
        this.problemRepository.deleteById(id);
    }
}
