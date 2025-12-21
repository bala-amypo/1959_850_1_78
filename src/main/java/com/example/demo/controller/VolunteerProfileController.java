package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.demo.model.VolunteerProfile;
import com.example.demo.repository.VolunteerProfileRepository;
import com.example.demo.dto.AvailabilityUpdateRequest;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.exception.BadRequestException;

@RestController
@RequestMapping("/volunteers")
public class VolunteerProfileController {
    @Autowired
    VolunteerProfileRepository repository;
    
    @PostMapping
    public VolunteerProfile create(@RequestBody VolunteerProfile volunteer) {
        return repository.save(volunteer);
    }
    
    @PatchMapping("/{id}/availability")
    public VolunteerProfile updateAvailability(@PathVariable Long id, @RequestBody AvailabilityUpdateRequest request) {
        VolunteerProfile volunteer = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Volunteer not found"));
        
        String status = request.getAvailabilityStatus();
        if (!"AVAILABLE".equals(status) && !"UNAVAILABLE".equals(status)) {
            throw new BadRequestException("Availability status must be AVAILABLE or UNAVAILABLE");
        }
        
        volunteer.setAvailabilityStatus(status);
        return repository.save(volunteer);
    }
}
