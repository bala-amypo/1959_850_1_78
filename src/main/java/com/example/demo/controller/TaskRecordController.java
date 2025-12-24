package com.example.demo.controller;

import com.example.demo.model.TaskAssignmentRecord;
import com.example.demo.model.TaskRecord;
import com.example.demo.service.TaskAssignmentService;
import com.example.demo.service.TaskRecordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
public class TaskRecordController {
    
    private final TaskRecordService taskRecordService;
    private final TaskAssignmentService taskAssignmentService;
    
    public TaskRecordController(TaskRecordService taskRecordService, TaskAssignmentService taskAssignmentService) {
        this.taskRecordService = taskRecordService;
        this.taskAssignmentService = taskAssignmentService;
    }
    
    @PostMapping
    public ResponseEntity<TaskRecord> createTask(@RequestBody TaskRecord task) {
        TaskRecord createdTask = taskRecordService.createTask(task);
        return ResponseEntity.ok(createdTask);
    }
    
    @PostMapping("/{taskId}/assign")
    public ResponseEntity<TaskAssignmentRecord> assignTask(@PathVariable Long taskId) {
        TaskAssignmentRecord assignment = taskAssignmentService.assignTask(taskId);
        return ResponseEntity.ok(assignment);
    }
}