package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.demo.model.TaskRecord;
import com.example.demo.model.TaskAssignmentRecord;
import com.example.demo.repository.TaskRecordRepository;
import com.example.demo.repository.TaskAssignmentRecordRepository;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/tasks")
public class TaskRecordController {
    @Autowired
    TaskRecordRepository repository;
    
    @Autowired
    TaskAssignmentRecordRepository assignmentRepository;
    
    @PostMapping
    public TaskRecord create(@RequestBody TaskRecord task) {
        if (task.getRequiredSkill() == null || task.getRequiredSkillLevel() == null) {
            throw new BadRequestException("Required skill and skill level must not be null");
        }
        task.setStatus("OPEN");
        return repository.save(task);
    }
    
    @PostMapping("/{taskId}/assign")
    public TaskAssignmentRecord assignTask(@PathVariable Long taskId) {
        TaskRecord task = repository.findById(taskId)
            .orElseThrow(() -> new ResourceNotFoundException("Task not found"));
        
        if (assignmentRepository.existsByTaskIdAndStatus(taskId, "ACTIVE")) {
            throw new BadRequestException("Task already has an ACTIVE assignment");
        }
        
        TaskAssignmentRecord assignment = new TaskAssignmentRecord();
        assignment.setTaskId(taskId);
        assignment.setVolunteerId(1L);
        assignment.setStatus("ACTIVE");
        
        task.setStatus("ACTIVE");
        repository.save(task);
        
        return assignmentRepository.save(assignment);
    }
}
