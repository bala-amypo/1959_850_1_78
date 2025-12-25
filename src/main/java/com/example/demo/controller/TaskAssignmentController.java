package com.example.demo.controller;

import com.example.demo.dto.AssignmentStatusUpdateRequest;
import com.example.demo.model.TaskAssignmentRecord;
import com.example.demo.service.TaskAssignmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/assignments")
public class TaskAssignmentController {
    
    private static final Logger logger = LoggerFactory.getLogger(TaskAssignmentController.class);
    private final TaskAssignmentService taskAssignmentService;
    
    public TaskAssignmentController(TaskAssignmentService taskAssignmentService) {
        this.taskAssignmentService = taskAssignmentService;
    }
    
    @GetMapping("/task/{taskId}")
    public ResponseEntity<List<TaskAssignmentRecord>> getAssignmentsByTask(@PathVariable Long taskId) {
        try {
            if (taskId == null) {
                logger.warn("Task ID is null");
                return ResponseEntity.badRequest().build();
            }
            List<TaskAssignmentRecord> assignments = taskAssignmentService.getAssignmentsByTask(taskId);
            return ResponseEntity.ok(assignments);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid argument for task assignments: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.error("Unexpected error getting assignments by task: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @GetMapping("/volunteer/{volunteerId}")
    public ResponseEntity<List<TaskAssignmentRecord>> getAssignmentsByVolunteer(@PathVariable Long volunteerId) {
        try {
            if (volunteerId == null) {
                logger.warn("Volunteer ID is null");
                return ResponseEntity.badRequest().build();
            }
            List<TaskAssignmentRecord> assignments = taskAssignmentService.getAssignmentsByVolunteer(volunteerId);
            return ResponseEntity.ok(assignments);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid argument for volunteer assignments: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.error("Unexpected error getting assignments by volunteer: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @PatchMapping("/{id}/status")
    public ResponseEntity<TaskAssignmentRecord> updateAssignmentStatus(
            @PathVariable Long id,
            @RequestBody AssignmentStatusUpdateRequest request) {
        try {
            if (id == null || request == null) {
                logger.warn("Invalid parameters for status update: id={}, request={}", id, request);
                return ResponseEntity.badRequest().build();
            }
            TaskAssignmentRecord assignment = taskAssignmentService.updateAssignmentStatus(id, request.getStatus());
            return ResponseEntity.ok(assignment);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid argument for assignment status update: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            logger.error("Runtime error during assignment status update: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        } catch (Exception e) {
            logger.error("Unexpected error during assignment status update: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}