package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.demo.model.TaskAssignmentRecord;
import com.example.demo.repository.TaskAssignmentRecordRepository;
import java.util.List;

@RestController
@RequestMapping("/assignments")
public class TaskAssignmentController {
    @Autowired
    TaskAssignmentRecordRepository repository;
    
    @GetMapping("/task/{taskId}")
    public List<TaskAssignmentRecord> getByTask(@PathVariable Long taskId) {
        return repository.findByTaskId(taskId);
    }
    
    @GetMapping("/volunteer/{volunteerId}")
    public List<TaskAssignmentRecord> getByVolunteer(@PathVariable Long volunteerId) {
        return repository.findByVolunteerId(volunteerId);
    }
}
