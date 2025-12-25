package com.example.demo.service.impl;

import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.TaskAssignmentRecord;
import com.example.demo.model.TaskRecord;
import com.example.demo.model.VolunteerProfile;
import com.example.demo.model.VolunteerSkillRecord;
import com.example.demo.repository.TaskAssignmentRecordRepository;
import com.example.demo.repository.TaskRecordRepository;
import com.example.demo.repository.VolunteerProfileRepository;
import com.example.demo.repository.VolunteerSkillRecordRepository;
import com.example.demo.service.TaskAssignmentService;
import com.example.demo.util.SkillLevelUtil;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TaskAssignmentServiceImpl implements TaskAssignmentService {
    
    private final TaskAssignmentRecordRepository taskAssignmentRecordRepository;
    private final TaskRecordRepository taskRecordRepository;
    private final VolunteerProfileRepository volunteerProfileRepository;
    private final VolunteerSkillRecordRepository volunteerSkillRecordRepository;
    
    public TaskAssignmentServiceImpl(TaskAssignmentRecordRepository taskAssignmentRecordRepository,
                                   TaskRecordRepository taskRecordRepository,
                                   VolunteerProfileRepository volunteerProfileRepository,
                                   VolunteerSkillRecordRepository volunteerSkillRecordRepository) {
        this.taskAssignmentRecordRepository = taskAssignmentRecordRepository;
        this.taskRecordRepository = taskRecordRepository;
        this.volunteerProfileRepository = volunteerProfileRepository;
        this.volunteerSkillRecordRepository = volunteerSkillRecordRepository;
    }
    
    @Override
    public TaskAssignmentRecord assignTask(Long taskId) {
        if (taskAssignmentRecordRepository.existsByTaskIdAndStatus(taskId, "ACTIVE")) {
            throw new BadRequestException("Task already has an ACTIVE assignment");
        }
        
        TaskRecord task = taskRecordRepository.findById(taskId)
            .orElseThrow(() -> new ResourceNotFoundException("Task not found"));
        
        List<VolunteerProfile> availableVolunteers = volunteerProfileRepository.findByAvailabilityStatus("AVAILABLE");
        if (availableVolunteers.isEmpty()) {
            throw new BadRequestException("No AVAILABLE volunteers found");
        }
        
        for (VolunteerProfile volunteer : availableVolunteers) {
            List<VolunteerSkillRecord> skills = volunteerSkillRecordRepository.findByVolunteerId(volunteer.getId());
            for (VolunteerSkillRecord skill : skills) {
                if (skill.getSkillName().equals(task.getRequiredSkill()) &&
                    SkillLevelUtil.levelRank(skill.getSkillLevel()) >= SkillLevelUtil.levelRank(task.getRequiredSkillLevel())) {
                    
                    TaskAssignmentRecord assignment = new TaskAssignmentRecord();
                    assignment.setTaskId(taskId);
                    assignment.setVolunteerId(volunteer.getId());
                    assignment.setStatus("ACTIVE");
                    
                    task.setStatus("ACTIVE");
                    taskRecordRepository.save(task);
                    
                    return taskAssignmentRecordRepository.save(assignment);
                }
            }
        }
        
        throw new BadRequestException("No volunteer meets the required skill level");
    }
    
    @Override
    public List<TaskAssignmentRecord> getAssignmentsByTask(Long taskId) {
        return taskAssignmentRecordRepository.findByTaskId(taskId);
    }
    
    @Override
    public List<TaskAssignmentRecord> getAssignmentsByVolunteer(Long volunteerId) {
        return taskAssignmentRecordRepository.findByVolunteerId(volunteerId);
    }
    
    @Override
    public TaskAssignmentRecord updateAssignmentStatus(Long assignmentId, String status) {
        TaskAssignmentRecord assignment = taskAssignmentRecordRepository.findById(assignmentId)
            .orElseThrow(() -> new ResourceNotFoundException("Assignment not found"));
        
        assignment.setStatus(status);
        return taskAssignmentRecordRepository.save(assignment);
    }
    
    public List<TaskAssignmentRecord> getAllAssignments() {
        return taskAssignmentRecordRepository.findAll();
    }
}