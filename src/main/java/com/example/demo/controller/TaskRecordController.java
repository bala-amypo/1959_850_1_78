package com.example.demo.controller;

import com.example.demo.model.TaskAssignmentRecord;
import com.example.demo.model.TaskRecord;
import com.example.demo.service.TaskAssignmentService;
import com.example.demo.service.TaskRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
public class TaskRecordController {
    
    private static final Logger logger = LoggerFactory.getLogger(TaskRecordController.class);
    private final TaskRecordService taskRecordService;
    private final TaskAssignmentService taskAssignmentService;
    
    public TaskRecordController(TaskRecordService taskRecordService, TaskAssignmentService taskAssignmentService) {
        this.taskRecordService = taskRecordService;
        this.taskAssignmentService = taskAssignmentService;
    }
    
    @PostMapping
    public ResponseEntity<TaskRecord> createTask(@RequestBody TaskRecord task) {
        try {
            if (task == null) {
                logger.warn("Task creation request is null");
                return ResponseEntity.badRequest().build();
            }
            TaskRecord createdTask = taskRecordService.createTask(task);
            return ResponseEntity.ok(createdTask);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid argument for task creation: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.error("Unexpected error during task creation: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @PostMapping("/{taskId}/assign")
    public ResponseEntity<TaskAssignmentRecord> assignTask(@PathVariable Long taskId) {
        try {
            if (taskId == null) {
                logger.warn("Task ID is null for assignment");
                return ResponseEntity.badRequest().build();
            }
            TaskAssignmentRecord assignment = taskAssignmentService.assignTask(taskId);
            return ResponseEntity.ok(assignment);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid argument for task assignment: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.error("Unexpected error during task assignment: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}