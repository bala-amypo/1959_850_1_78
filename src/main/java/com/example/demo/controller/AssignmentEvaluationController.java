package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.demo.model.AssignmentEvalutionRecord;
import com.example.demo.repository.AssignmentEvalutionRecordRepository;
import com.example.demo.dto.EvalutionRequest;

@RestController
@RequestMapping("/evaluations")
public class AssignmentEvalutionController {
    @Autowired
    AssignmentEvalutionRecordRepository repository;
    
    @PostMapping
    public AssignmentEvalutionRecord create(@RequestBody EvalutionRequest request) {
        AssignmentEvalutionRecord evaluation = new AssignmentEvalutionRecord();
        evaluation.setAssignmentId(request.getAssignmentId());
        evaluation.setRating(request.getRating());
        evaluation.setComments(request.getComments());
        return repository.save(evaluation);
    }
}
