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
            // Check if email already exists
            if (userService.findByEmail(request.getEmail()) != null) {
                logger.error("User already exists: {}", request.getEmail());
                return ResponseEntity.badRequest().build();
            }
            
            // Create user first
            User user = userService.createUser(request.getEmail(), request.getPassword(), request.getRole());
            logger.info("User created successfully with ID: {}", user.getId());
            
            // Then create volunteer profile
            VolunteerProfile volunteer = volunteerProfileService.registerVolunteer(request);
            logger.info("Volunteer profile created successfully with ID: {}", volunteer.getId());
            
            return ResponseEntity.ok(volunteer);
        } catch (Exception e) {
            logger.error("Registration failed: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        try {
            if (request == null || request.getEmail() == null || request.getPassword() == null) {
                logger.warn("Invalid login request: missing email or password");
                return ResponseEntity.badRequest().build();
            }
            
            String email = request.getEmail();
            logger.info("Attempting login for user: {}", email);
            
            // Check if user exists
            User user = userService.findByEmail(email);
            if (user == null) {
                logger.error("User not found in database: {}", email);
                return ResponseEntity.status(401).build();
            }
            logger.info("User found in database: {}", email);
            
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, request.getPassword())
            );
            
            String token = jwtTokenProvider.generateToken(email, user.getRole());
            return ResponseEntity.ok(new AuthResponse(token, user.getId(), user.getRole()));
        } catch (BadCredentialsException e) {
            logger.error("Authentication failed for user: {}", request.getEmail());
            return ResponseEntity.status(401).build();
        } catch (IllegalArgumentException e) {
            logger.error("Invalid argument during login: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            logger.error("Runtime error during login: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        } catch (Exception e) {
            logger.error("Unexpected error during login: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}