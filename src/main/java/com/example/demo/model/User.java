package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
@Table(name = "users")
public class User {
    private static final Logger logger = LoggerFactory.getLogger(User.class);
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true)
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email must be valid")
    private String email;
    
    @NotBlank(message = "Password cannot be blank")
    private String password;
    private String role;
    
    public User() {}
    
    public User(String email, String password, String role) {
        try {
            if (email == null || email.trim().isEmpty()) {
                logger.error("Email validation failed: null or empty");
                throw new IllegalArgumentException("Email cannot be null or empty");
            }
            if (password == null || password.trim().isEmpty()) {
                logger.error("Password validation failed: null or empty");
                throw new IllegalArgumentException("Password cannot be null or empty");
            }
            if (role == null || role.trim().isEmpty()) {
                logger.error("Role validation failed: null or empty");
                throw new IllegalArgumentException("Role cannot be null or empty");
            }
            this.email = email;
            this.password = password;
            this.role = role;
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error in User constructor: {}", e.getMessage());
            throw new IllegalArgumentException("Invalid user parameters", e);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email != null && email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password != null && password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        if (role != null && role.trim().isEmpty()) {
            throw new IllegalArgumentException("Role cannot be empty");
        }
        this.role = role;
    }
}