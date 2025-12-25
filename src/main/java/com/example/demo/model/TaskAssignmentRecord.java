package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "task_assignment_records")
public class TaskAssignmentRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Long taskId;
    private Long volunteerId;
    private String status;
    
    public TaskAssignmentRecord() {
        this.status = "ACTIVE";
    }
    
    public TaskAssignmentRecord(Long taskId, Long volunteerId, String status) {
        if (taskId == null) {
            throw new IllegalArgumentException("Task ID cannot be null");
        }
        if (volunteerId == null) {
            throw new IllegalArgumentException("Volunteer ID cannot be null");
        }
        this.taskId = taskId;
        this.volunteerId = volunteerId;
        this.status = status != null ? status : "ACTIVE";
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getTaskId() {
        return taskId;
    }
    
    public void setTaskId(Long taskId) {
        if (taskId == null) {
            throw new IllegalArgumentException("Task ID cannot be null");
        }
        this.taskId = taskId;
    }
    
    public Long getVolunteerId() {
        return volunteerId;
    }
    
    public void setVolunteerId(Long volunteerId) {
        if (volunteerId == null) {
            throw new IllegalArgumentException("Volunteer ID cannot be null");
        }
        this.volunteerId = volunteerId;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
}