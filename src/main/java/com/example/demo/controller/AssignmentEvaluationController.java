package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.demo.model.AssignmentEvaluationRecord;
import com.example.demo.repository.AssignmentEvaluationRecordRepository;
import com.example.demo.dto.EvalutionRequest;

@RestController
@RequestMapping("/evaluations")
public class AssignmentEvaluationController {
    @Autowired
    AssignmentEvaluationRecordRepository repository;
    
    @PostMapping
    public AssignmentEvaluationRecord create(@RequestBody EvalutionRequest request) {
        AssignmentEvaluationRecord evaluation = new AssignmentEvaluationRecord();
        evaluation.setAssignmentId(request.getAssignmentId());
        evaluation.setRating(request.getRating());
        evaluation.setComments(request.getComments());
        return repository.save(evaluation);
    }
}
