package com.example.demo.service;

import com.example.demo.model.TaskAssignmentRecord;
import java.util.List;

public interface TaskAssignmentService {
    TaskAssignmentRecord assignTask(Long taskId);
    List<TaskAssignmentRecord> getAssignmentsByTask(Long taskId);
    List<TaskAssignmentRecord> getAssignmentsByVolunteer(Long volunteerId);
    TaskAssignmentRecord updateAssignmentStatus(Long assignmentId, String status);
}