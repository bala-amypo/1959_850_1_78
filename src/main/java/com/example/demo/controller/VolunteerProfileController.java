package com.example.demo.controller;

import com.example.demo.dto.AvailabilityUpdateRequest;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.model.VolunteerProfile;
import com.example.demo.service.VolunteerProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/volunteers")
public class VolunteerProfileController {
    
    private static final Logger logger = LoggerFactory.getLogger(VolunteerProfileController.class);
    private final VolunteerProfileService volunteerProfileService;
    
    public VolunteerProfileController(VolunteerProfileService volunteerProfileService) {
        this.volunteerProfileService = volunteerProfileService;
    }
    
    @PostMapping
    public ResponseEntity<VolunteerProfile> registerVolunteer(@RequestBody RegisterRequest request) {
        try {
            if (request == null) {
                logger.warn("Registration request is null");
                return ResponseEntity.badRequest().build();
            }
            VolunteerProfile volunteer = volunteerProfileService.registerVolunteer(request);
            return ResponseEntity.ok(volunteer);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid argument for volunteer registration: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            logger.error("Runtime error during volunteer registration: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        } catch (Exception e) {
            logger.error("Unexpected error during volunteer registration: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @PatchMapping("/{id}/availability")
    public ResponseEntity<VolunteerProfile> updateAvailability(
            @PathVariable Long id, 
            @RequestBody AvailabilityUpdateRequest request) {
        try {
            if (id == null || request == null) {
                logger.warn("Invalid parameters for availability update: id={}, request={}", id, request);
                return ResponseEntity.badRequest().build();
            }
            VolunteerProfile volunteer = volunteerProfileService.updateAvailability(id, request.getAvailabilityStatus());
            return ResponseEntity.ok(volunteer);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid argument for availability update: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            logger.error("Runtime error during availability update: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        } catch (Exception e) {
            logger.error("Unexpected error during availability update: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}