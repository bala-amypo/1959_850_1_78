package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.model.TaskRecord;

@Repository
public interface TaskRecordRepository extends JpaRepository<TaskRecord, Long> {
}
