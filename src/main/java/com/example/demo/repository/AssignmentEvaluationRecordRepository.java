package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.model.AssignmentEvaluationRecord;

@Repository
public interface AssignmentEvaluationRecordRepository extends JpaRepository<AssignmentEvaluationRecord, Long> {
}
