package com.algojudge.algojudge.repository;

import com.algojudge.algojudge.entity.Problem;
import com.algojudge.algojudge.entity.Submission;
import com.algojudge.algojudge.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission,Integer> {
    List<Submission> findByUserAndProblemOrderByCreatedOnDesc(User user, Problem problem);
}
