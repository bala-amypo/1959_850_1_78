package com.example.pro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.pro.dto.AvailabilityUpdateRequest;
import com.example.pro.exception.ResourceNotFoundException;
import com.example.pro.model.VolunteerProfile;
import com.example.pro.repository.VolunteerProfileRepository;

@RestController
@RequestMapping("/volunteers")
public class VolunteerProfileController {
    @Autowired
    VolunteerProfileRepository profileRepository;


    @PostMapping
    public VolunteerProfile create(@RequestBody VolunteerProfile volunteer){
        return profileRepository.save(volunteer);
    }

    @PatchMapping("/{id}/availability")
    public VolunteerProfile updateAvailability(@PathVariable Long id, @RequestBody AvailabilityUpdateRequest request){
        String status = request.getAvailabilityStatus();
        if (!"AVAILABLE".equals(status) && !"UNAVAILABLE".equals(status)) {
            throw new IllegalArgumentException("Invalid availability status. Must be AVAILABLE or UNAVAILABLE");
        }
        
        VolunteerProfile volunteer = profileRepository.findById(id)
           .orElseThrow(() -> new ResourceNotFoundException("Volunteer not found"));
        volunteer.setAvailabilityStatus(status);
        return profileRepository.save(volunteer);
    }

}