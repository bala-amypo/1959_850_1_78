package com.example.demo.controller;

import com.example.demo.dto.AuthRequest;
import com.example.demo.dto.AuthResponse;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.model.User;
import com.example.demo.model.VolunteerProfile;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.VolunteerProfileRepository;
import com.example.demo.security.JwtTokenProvider;
import com.example.demo.service.UserService;
import com.example.demo.service.VolunteerProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final VolunteerProfileService volunteerProfileService;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final VolunteerProfileRepository volunteerProfileRepository;
    private final PasswordEncoder passwordEncoder;
    
    public AuthController(VolunteerProfileService volunteerProfileService,
                         UserService userService,
                         JwtTokenProvider jwtTokenProvider,
                         AuthenticationManager authenticationManager,
                         UserRepository userRepository,
                         VolunteerProfileRepository volunteerProfileRepository,
                         PasswordEncoder passwordEncoder) {
        this.volunteerProfileService = volunteerProfileService;
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.volunteerProfileRepository = volunteerProfileRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            // Create and save user
            User user = new User();
            user.setEmail(request.getEmail());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setRole(request.getRole());
            User savedUser = userRepository.save(user);
            
            // Create volunteer profile
            VolunteerProfile volunteer = new VolunteerProfile();
            volunteer.setName(request.getName());
            volunteer.setEmail(request.getEmail());
            volunteer.setAvailabilityStatus("AVAILABLE");
            VolunteerProfile savedVolunteer = volunteerProfileRepository.save(volunteer);
            
            return ResponseEntity.ok(savedVolunteer);
        } catch (Exception e) {
            logger.error("Registration failed: {}", e.getMessage());
            return ResponseEntity.badRequest().body("Registration failed: " + e.getMessage());
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