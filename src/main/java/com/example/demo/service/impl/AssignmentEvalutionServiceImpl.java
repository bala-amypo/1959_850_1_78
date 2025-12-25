package com.example.demo.service.impl;

import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.AssignmentEvaluationRecord;
import com.example.demo.model.TaskAssignmentRecord;
import com.example.demo.repository.AssignmentEvaluationRecordRepository;
import com.example.demo.repository.TaskAssignmentRecordRepository;
import com.example.demo.service.AssignmentEvaluationService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AssignmentEvaluationServiceImpl implements AssignmentEvaluationService {
    
    private final AssignmentEvaluationRecordRepository assignmentEvaluationRecordRepository;
    private final TaskAssignmentRecordRepository taskAssignmentRecordRepository;
    
    public AssignmentEvaluationServiceImpl(AssignmentEvaluationRecordRepository assignmentEvaluationRecordRepository,
                                         TaskAssignmentRecordRepository taskAssignmentRecordRepository) {
        this.assignmentEvaluationRecordRepository = assignmentEvaluationRecordRepository;
        this.taskAssignmentRecordRepository = taskAssignmentRecordRepository;
    }
    
    @Override
    public AssignmentEvaluationRecord evaluateAssignment(AssignmentEvaluationRecord evaluation) {
        TaskAssignmentRecord assignment = taskAssignmentRecordRepository.findById(evaluation.getAssignmentId())
            .orElseThrow(() -> new ResourceNotFoundException("Assignment not found"));
        
        if (!"COMPLETED".equals(assignment.getStatus())) {
            throw new BadRequestException("Assignment must be COMPLETED before evaluation");
        }
        
        return assignmentEvaluationRecordRepository.save(evaluation);
    }
    
    public List<AssignmentEvaluationRecord> getEvaluationsByAssignment(Long assignmentId) {
        return assignmentEvaluationRecordRepository.findByAssignmentId(assignmentId);
    }
}