package com.example.demo.controller;

import com.example.demo.dto.EvaluationRequest;
import com.example.demo.model.AssignmentEvaluationRecord;
import com.example.demo.service.AssignmentEvaluationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/evaluations")
public class AssignmentEvaluationController {
    
    private final AssignmentEvaluationService assignmentEvaluationService;
    
    public AssignmentEvaluationController(AssignmentEvaluationService assignmentEvaluationService) {
        this.assignmentEvaluationService = assignmentEvaluationService;
    }
    
    @PostMapping
    public ResponseEntity<AssignmentEvaluationRecord> evaluateAssignment(@RequestBody EvaluationRequest request) {
        AssignmentEvaluationRecord evaluation = new AssignmentEvaluationRecord(
            request.getAssignmentId(), 
            request.getRating(), 
            request.getComments()
        );
        AssignmentEvaluationRecord savedEvaluation = assignmentEvaluationService.evaluateAssignment(evaluation);
        return ResponseEntity.ok(savedEvaluation);
    }
}