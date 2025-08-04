package com.algojudge.algojudge.service;

import com.algojudge.algojudge.entity.Testcase;
import com.algojudge.algojudge.repository.ProblemRepository;
import com.algojudge.algojudge.repository.TestcaseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TestcaseService {
    private final TestcaseRepository testcaseRepository;

    public TestcaseService(TestcaseRepository testcaseRepository, ProblemRepository problemRepository) {
        this.testcaseRepository = testcaseRepository;
    }

    public Testcase saveTestcase(Testcase testcase){
        return testcaseRepository.save(testcase);
    }

    public Optional<Testcase> getTestcaseById(int id) {
        return testcaseRepository.findById(id);
    }

    public List<Testcase> getAllTestcases() {
        return testcaseRepository.findAll();
    }

    public void deleteTestcaseById(int id) {
        testcaseRepository.deleteById(id);
    }

    public List<Testcase> getAllTestcases(int problemId){
        return testcaseRepository.findByProblem_Id(problemId);
    }
}
