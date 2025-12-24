package com.example.demo.controller;

import com.example.demo.dto.AuthRequest;
import com.example.demo.dto.AuthResponse;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.model.VolunteerProfile;
import com.example.demo.security.JwtTokenProvider;
import com.example.demo.service.VolunteerProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    
    private final VolunteerProfileService volunteerProfileService;
    private final JwtTokenProvider jwtTokenProvider;
    
    public AuthController(VolunteerProfileService volunteerProfileService, 
                         JwtTokenProvider jwtTokenProvider) {
        this.volunteerProfileService = volunteerProfileService;
        this.jwtTokenProvider = jwtTokenProvider;
    }
    
    @PostMapping("/register")
    public ResponseEntity<VolunteerProfile> register(@RequestBody RegisterRequest request) {
        VolunteerProfile volunteer = volunteerProfileService.registerVolunteer(request);
        return ResponseEntity.ok(volunteer);
    }
    
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        String token = jwtTokenProvider.generateToken(request.getEmail(), "USER");
        AuthResponse response = new AuthResponse(token, 1L, "USER");
        return ResponseEntity.ok(response);
    }
}