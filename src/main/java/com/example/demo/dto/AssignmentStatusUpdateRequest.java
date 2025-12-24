package com.example.demo.dto;

public class AssignmentStatusUpdateRequest {
    private Long assignmentId;
    private String status;
    
    public AssignmentStatusUpdateRequest() {}
    
    public AssignmentStatusUpdateRequest(Long assignmentId, String status) {
        this.assignmentId = assignmentId;
        this.status = status;
    }
    
    public Long getAssignmentId() { return assignmentId; }
    public void setAssignmentId(Long assignmentId) { this.assignmentId = assignmentId; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}