package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.demo.model.AssignmentEvalutionRecord;
import com.example.demo.repository.AssignmentEvalutionRecordRepository;
import com.example.demo.dto.EvaluationRequest;

@RestController
@RequestMapping("/evaluations")
public class AssignmentEvaluationController {
    @Autowired
    AssignmentEvalutionRecordRepository repository;
    
    @PostMapping
    public AssignmentEvaluationRecord create(@RequestBody EvaluationRequest request) {
        AssignmentEvaluationRecord evaluation = new AssignmentEvaluationRecord();
        evaluation.setAssignmentId(request.getAssignmentId());
        evaluation.setRating(request.getRating());
        evaluation.setComments(request.getComments());
        return repository.save(evaluation);
    }
}
