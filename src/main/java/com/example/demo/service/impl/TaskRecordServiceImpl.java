package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.TaskRecord;
import com.example.demo.repository.TaskRecordRepository;
import com.example.demo.service.TaskRecordService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TaskRecordServiceImpl implements TaskRecordService {
    
    private final TaskRecordRepository taskRecordRepository;
    
    public TaskRecordServiceImpl(TaskRecordRepository taskRecordRepository) {
        this.taskRecordRepository = taskRecordRepository;
    }
    
    @Override
    public TaskRecord createTask(TaskRecord task) {
        task.setStatus("OPEN");
        return taskRecordRepository.save(task);
    }
    
    @Override
    public TaskRecord getTaskById(Long id) {
        return taskRecordRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Task not found"));
    }
    
    public TaskRecord updateTask(Long id, TaskRecord task) {
        TaskRecord existing = getTaskById(id);
        existing.setTaskName(task.getTaskName());
        existing.setRequiredSkill(task.getRequiredSkill());
        existing.setRequiredSkillLevel(task.getRequiredSkillLevel());
        existing.setPriority(task.getPriority());
        existing.setStatus(task.getStatus());
        return taskRecordRepository.save(existing);
    }
    
    public List<TaskRecord> getOpenTasks() {
        return taskRecordRepository.findByStatus("OPEN");
    }
    
    public Optional<TaskRecord> getTaskByCode(String taskCode) {
        return taskRecordRepository.findByTaskCode(taskCode);
    }
    
    public List<TaskRecord> getAllTasks() {
        return taskRecordRepository.findAll();
    }
}