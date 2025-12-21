package com.example.demo.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "task_assignment_records")
public class TaskAssignmentRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long taskId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "taskId", insertable = false, updatable = false)
    private TaskRecord task;

    @Column(nullable = false)
    private Long volunteerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "volunteerId", insertable = false, updatable = false)
    private VolunteerProfile volunteer;

    @Column(nullable = false)
    private String status;

    @OneToMany(mappedBy = "assignment", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AssignmentEvalutionRecord> evaluations;

    public TaskAssignmentRecord() {}

    public TaskAssignmentRecord(Long taskId, Long volunteerId, String status) {
        this.taskId = taskId;
        this.volunteerId = volunteerId;
        this.status = status;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getTaskId() { return taskId; }
    public void setTaskId(Long taskId) { this.taskId = taskId; }
    public TaskRecord getTask() { return task; }
    public void setTask(TaskRecord task) { this.task = task; }
    public Long getVolunteerId() { return volunteerId; }
    public void setVolunteerId(Long volunteerId) { this.volunteerId = volunteerId; }
    public VolunteerProfile getVolunteer() { return volunteer; }
    public void setVolunteer(VolunteerProfile volunteer) { this.volunteer = volunteer; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public List<AssignmentEvalutionRecord> getEvaluations() { return evaluations; }
    public void setEvaluations(List<AssignmentEvalutionRecord> evaluations) { this.evaluations = evaluations; }
}
