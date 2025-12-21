package com.example.demo.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "task_records")
public class TaskRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    @Column(nullable = false)
    private String requiredSkill;

    @Column(nullable = false)
    private String requiredSkillLevel;

    @Column(nullable = false)
    private String status;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TaskAssignmentRecord> assignments;

    public TaskRecord() {}

    public TaskRecord(String title, String description, String requiredSkill, String requiredSkillLevel, String status) {
        this.title = title;
        this.description = description;
        this.requiredSkill = requiredSkill;
        this.requiredSkillLevel = requiredSkillLevel;
        this.status = status;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getRequiredSkill() { return requiredSkill; }
    public void setRequiredSkill(String requiredSkill) { this.requiredSkill = requiredSkill; }
    public String getRequiredSkillLevel() { return requiredSkillLevel; }
    public void setRequiredSkillLevel(String requiredSkillLevel) { this.requiredSkillLevel = requiredSkillLevel; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public List<TaskAssignmentRecord> getAssignments() { return assignments; }
    public void setAssignments(List<TaskAssignmentRecord> assignments) { this.assignments = assignments; }
}
