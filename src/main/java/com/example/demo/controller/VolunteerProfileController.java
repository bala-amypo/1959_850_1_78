package com.example.demo.controller;

import com.example.demo.dto.AvailabilityUpdateRequest;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.model.VolunteerProfile;
import com.example.demo.service.VolunteerProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/volunteers")
public class VolunteerProfileController {
    
    private final VolunteerProfileService volunteerProfileService;
    
    public VolunteerProfileController(VolunteerProfileService volunteerProfileService) {
        this.volunteerProfileService = volunteerProfileService;
    }
    
    @PostMapping
    public ResponseEntity<VolunteerProfile> registerVolunteer(@RequestBody RegisterRequest request) {
        VolunteerProfile volunteer = volunteerProfileService.registerVolunteer(request);
        return ResponseEntity.ok(volunteer);
    }
    
    @PatchMapping("/{id}/availability")
    public ResponseEntity<VolunteerProfile> updateAvailability(
            @PathVariable Long id, 
            @RequestBody AvailabilityUpdateRequest request) {
        VolunteerProfile volunteer = volunteerProfileService.updateAvailability(id, request.getAvailabilityStatus());
        return ResponseEntity.ok(volunteer);
    }
}