package com.example.demo.controller;

import com.example.demo.dto.AuthRequest;
import com.example.demo.dto.AuthResponse;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.model.User;
import com.example.demo.model.VolunteerProfile;
import com.example.demo.security.JwtTokenProvider;
import com.example.demo.service.UserService;
import com.example.demo.service.VolunteerProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final VolunteerProfileService volunteerProfileService;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    
    public AuthController(VolunteerProfileService volunteerProfileService,
                         UserService userService,
                         JwtTokenProvider jwtTokenProvider,
                         AuthenticationManager authenticationManager) {
        this.volunteerProfileService = volunteerProfileService;
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
    }
    
    @PostMapping("/register")
    public ResponseEntity<VolunteerProfile> register(@RequestBody RegisterRequest request) {
        try {
            if (userService.findByEmail(request.getEmail()) != null) {
                logger.error("User already exists: {}", request.getEmail());
                return ResponseEntity.badRequest().build();
            }
            
            // Create user and profile
            userService.createUser(request.getEmail(), request.getPassword(), request.getRole());
            VolunteerProfile volunteer = volunteerProfileService.registerVolunteer(request);
            
            logger.info("Registration successful for: {}", request.getEmail());
            return ResponseEntity.ok(volunteer);
        } catch (Exception e) {
            logger.error("Registration failed: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        try {
            // Authenticate using the manager defined in SecurityConfig
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            // Fetch user to generate JWT token
            User user = userService.findByEmail(request.getEmail());
            String token = jwtTokenProvider.createToken(user.getEmail(), user.getRole());

            logger.info("User logged in: {}", user.getEmail());
            return ResponseEntity.ok(new AuthResponse(token, user.getEmail(), user.getRole()));
        } catch (BadCredentialsException e) {
            logger.error("Invalid login attempt: {}", request.getEmail());
            return ResponseEntity.status(401).build();
        } catch (Exception e) {
            logger.error("Login error: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}
