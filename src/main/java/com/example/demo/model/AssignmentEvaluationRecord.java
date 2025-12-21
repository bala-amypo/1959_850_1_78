package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "assignment_evaluation_records")
public class AssignmentEvaluationRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long assignmentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignmentId", insertable = false, updatable = false)
    private TaskAssignmentRecord assignment;

    @Column(nullable = false)
    private Integer rating;

    private String comments;

    public AssignmentEvaluationRecord() {}

    public AssignmentEvaluationRecord(Long assignmentId, Integer rating, String comments) {
        this.assignmentId = assignmentId;
        this.rating = rating;
        this.comments = comments;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getAssignmentId() { return assignmentId; }
    public void setAssignmentId(Long assignmentId) { this.assignmentId = assignmentId; }
    public TaskAssignmentRecord getAssignment() { return assignment; }
    public void setAssignment(TaskAssignmentRecord assignment) { this.assignment = assignment; }
    public Integer getRating() { return rating; }
    public void setRating(Integer rating) { this.rating = rating; }
    public String getComments() { return comments; }
    public void setComments(String comments) { this.comments = comments; }
}
