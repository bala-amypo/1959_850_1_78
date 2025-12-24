package com.example.demo.controller;

import com.example.demo.model.TaskAssignmentRecord;
import com.example.demo.service.TaskAssignmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/assignments")
public class TaskAssignmentController {
    
    private final TaskAssignmentService taskAssignmentService;
    
    public TaskAssignmentController(TaskAssignmentService taskAssignmentService) {
        this.taskAssignmentService = taskAssignmentService;
    }
    
    @GetMapping("/task/{taskId}")
    public ResponseEntity<List<TaskAssignmentRecord>> getAssignmentsByTask(@PathVariable Long taskId) {
        List<TaskAssignmentRecord> assignments = taskAssignmentService.getAssignmentsByTask(taskId);
        return ResponseEntity.ok(assignments);
    }
    
    @GetMapping("/volunteer/{volunteerId}")
    public ResponseEntity<List<TaskAssignmentRecord>> getAssignmentsByVolunteer(@PathVariable Long volunteerId) {
        List<TaskAssignmentRecord> assignments = taskAssignmentService.getAssignmentsByVolunteer(volunteerId);
        return ResponseEntity.ok(assignments);
    }
}