package com.example.demo.controller;

import com.example.demo.dto.EvaluationRequest;
import com.example.demo.model.AssignmentEvaluationRecord;
import com.example.demo.service.AssignmentEvaluationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/evaluations")
public class AssignmentEvaluationController {
    
    private static final Logger logger = LoggerFactory.getLogger(AssignmentEvaluationController.class);
    private final AssignmentEvaluationService assignmentEvaluationService;
    
    public AssignmentEvaluationController(AssignmentEvaluationService assignmentEvaluationService) {
        this.assignmentEvaluationService = assignmentEvaluationService;
    }
    
    @PostMapping
    public ResponseEntity<AssignmentEvaluationRecord> evaluateAssignment(@RequestBody EvaluationRequest request) {
        try {
            if (request == null) {
                return ResponseEntity.badRequest().build();
            }

            // Optional: Identify which authenticated user is performing the evaluation
            String evaluatorEmail = SecurityContextHolder.getContext().getAuthentication().getName();
            logger.info("Evaluation for assignment {} by {}", request.getAssignmentId(), evaluatorEmail);

            AssignmentEvaluationRecord evaluation = new AssignmentEvaluationRecord(
                request.getAssignmentId(), 
                request.getRating(), 
                request.getComments()
            );
            
            AssignmentEvaluationRecord savedEvaluation = assignmentEvaluationService.evaluateAssignment(evaluation);
            return ResponseEntity.ok(savedEvaluation);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.error("Unexpected error: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}
