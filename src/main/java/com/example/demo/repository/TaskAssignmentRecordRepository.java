package com.example.demo.repository;

import com.example.demo.model.TaskAssignmentRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repository interface for managing TaskAssignmentRecord entities.
 * Provides CRUD operations and custom query methods for task assignments.
 */
@Repository
public interface TaskAssignmentRecordRepository extends JpaRepository<TaskAssignmentRecord, Long> {
    
    /**
     * Checks if a task assignment exists with the given task ID and status.
     * @param taskId the task ID to check
     * @param status the status to check
     * @return true if assignment exists, false otherwise
     */
    boolean existsByTaskIdAndStatus(Long taskId, String status);
    
    /**
     * Finds all task assignments for a specific task.
     * @param taskId the task ID to search for
     * @return list of task assignments for the task
     */
    List<TaskAssignmentRecord> findByTaskId(Long taskId);
    
    /**
     * Finds all task assignments for a specific volunteer.
     * @param volunteerId the volunteer ID to search for
     * @return list of task assignments for the volunteer
     */
    List<TaskAssignmentRecord> findByVolunteerId(Long volunteerId);
}