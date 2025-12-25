package com.example.demo.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AssignmentStatusUpdateRequest {
    private static final Logger logger = LoggerFactory.getLogger(AssignmentStatusUpdateRequest.class);
    private Long assignmentId;
    private String status;
    
    public AssignmentStatusUpdateRequest() {}
    
    public AssignmentStatusUpdateRequest(Long assignmentId, String status) {
        try {
            if (assignmentId == null) {
                try {
                    logger.error("Assignment ID is null in constructor");
                } catch (Exception logEx) {
                    // Fallback if logging fails
                }
                throw new IllegalArgumentException("Assignment ID cannot be null");
            }
            if (status == null || status.trim().isEmpty()) {
                try {
                    logger.error("Status is null or empty in constructor");
                } catch (Exception logEx) {
                    // Fallback if logging fails
                }
                throw new IllegalArgumentException("Status cannot be null or empty");
            }
            this.assignmentId = assignmentId;
            this.status = status;
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error in constructor: {}", e.getMessage());
            throw new IllegalArgumentException("Failed to create assignment status update request", e);
        }
    }
    
    public Long getAssignmentId() { return assignmentId; }
    
    public void setAssignmentId(Long assignmentId) {
        if (assignmentId == null) {
            throw new IllegalArgumentException("Assignment ID cannot be null");
        }
        this.assignmentId = assignmentId;
    }
    
    public String getStatus() { return status; }
    
    public void setStatus(String status) {
        if (status == null || status.trim().isEmpty()) {
            throw new IllegalArgumentException("Status cannot be null or empty");
        }
        this.status = status;
    }
}