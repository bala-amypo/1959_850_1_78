package com.example.demo.controller;

import com.example.demo.model.VolunteerSkillRecord;
import com.example.demo.service.VolunteerSkillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/volunteer-skills")
public class VolunteerSkillController {
    
    private static final Logger logger = LoggerFactory.getLogger(VolunteerSkillController.class);
    private final VolunteerSkillService volunteerSkillService;
    
    public VolunteerSkillController(VolunteerSkillService volunteerSkillService) {
        this.volunteerSkillService = volunteerSkillService;
    }
    
    @GetMapping("/volunteer/{volunteerId}")
    public ResponseEntity<List<VolunteerSkillRecord>> getSkillsByVolunteer(@PathVariable Long volunteerId) {
        try {
            if (volunteerId == null) {
                logger.warn("Volunteer ID is null");
                return ResponseEntity.badRequest().build();
            }
            List<VolunteerSkillRecord> skills = volunteerSkillService.getSkillsByVolunteer(volunteerId);
            return ResponseEntity.ok(skills);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid argument for getting skills by volunteer: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.error("Unexpected error getting skills by volunteer: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @PostMapping
    public ResponseEntity<VolunteerSkillRecord> addSkill(@RequestBody VolunteerSkillRecord skill) {
        try {
            if (skill == null) {
                logger.warn("Skill record is null");
                return ResponseEntity.badRequest().build();
            }
            VolunteerSkillRecord createdSkill = volunteerSkillService.addSkill(skill);
            return ResponseEntity.ok(createdSkill);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid argument for adding skill: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.error("Unexpected error adding skill: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @PutMapping("/{skillId}")
    public ResponseEntity<VolunteerSkillRecord> updateSkill(
            @PathVariable Long skillId,
            @RequestBody VolunteerSkillRecord skill) {
        try {
            if (skillId == null || skill == null) {
                logger.warn("Invalid parameters for skill update: skillId={}, skill={}", skillId, skill);
                return ResponseEntity.badRequest().build();
            }
            VolunteerSkillRecord modifiedSkill = volunteerSkillService.updateSkill(skillId, skill);
            return ResponseEntity.ok(modifiedSkill);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid argument for updating skill: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.error("Unexpected error updating skill: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}