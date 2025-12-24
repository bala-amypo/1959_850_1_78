package com.example.demo.controller;

import com.example.demo.model.VolunteerSkillRecord;
import com.example.demo.service.VolunteerSkillService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/volunteer-skills")
public class VolunteerSkillController {
    
    private final VolunteerSkillService volunteerSkillService;
    
    public VolunteerSkillController(VolunteerSkillService volunteerSkillService) {
        this.volunteerSkillService = volunteerSkillService;
    }
    
    @GetMapping("/volunteer/{volunteerId}")
    public ResponseEntity<List<VolunteerSkillRecord>> getSkillsByVolunteer(@PathVariable Long volunteerId) {
        List<VolunteerSkillRecord> skills = volunteerSkillService.getSkillsByVolunteer(volunteerId);
        return ResponseEntity.ok(skills);
    }
}