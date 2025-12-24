package com.example.demo.dto;

public class EvaluationRequest {
    private Long assignmentId;
    private Integer rating;
    private String comments;
    
    public EvaluationRequest() {}
    
    public EvaluationRequest(Long assignmentId, Integer rating, String comments) {
        this.assignmentId = assignmentId;
        this.rating = rating;
        this.comments = comments;
    }
    
    public Long getAssignmentId() { return assignmentId; }
    public void setAssignmentId(Long assignmentId) { this.assignmentId = assignmentId; }
    
    public Integer getRating() { return rating; }
    public void setRating(Integer rating) { this.rating = rating; }
    
    public String getComments() { return comments; }
    public void setComments(String comments) { this.comments = comments; }
}