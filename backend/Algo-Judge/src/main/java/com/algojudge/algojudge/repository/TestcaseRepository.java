package com.algojudge.algojudge.repository;

import com.algojudge.algojudge.entity.Testcase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestcaseRepository extends JpaRepository<Testcase,Integer> {
    List<Testcase> findByProblem_Id(int problemId);
}
