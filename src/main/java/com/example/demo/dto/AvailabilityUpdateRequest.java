package com.example.demo.dto;

/**
 * Data Transfer Object for updating volunteer availability status.
 */
public class AvailabilityUpdateRequest {
    /** The unique identifier of the volunteer */
    private Long volunteerId;
    
    /** The new availability status for the volunteer */
    private String availabilityStatus;
    
    /** Default constructor */
    public AvailabilityUpdateRequest() {}
    
    /**
     * Constructor with validation
     * @param volunteerId The volunteer ID (must be positive)
     * @param availabilityStatus The availability status (cannot be null or empty)
     */
    public AvailabilityUpdateRequest(Long volunteerId, String availabilityStatus) {
        if (volunteerId == null || volunteerId <= 0) {
            throw new IllegalArgumentException("Volunteer ID must be positive");
        }
        if (availabilityStatus == null || availabilityStatus.trim().isEmpty()) {
            throw new IllegalArgumentException("Availability status cannot be null or empty");
        }
        this.volunteerId = volunteerId;
        this.availabilityStatus = availabilityStatus;
    }
    
    public Long getVolunteerId() {
        return volunteerId;
    }
    
    public void setVolunteerId(Long volunteerId) {
        if (volunteerId != null && volunteerId <= 0) {
            throw new IllegalArgumentException("Volunteer ID must be positive");
        }
        this.volunteerId = volunteerId;
    }
    
    public String getAvailabilityStatus() {
        return availabilityStatus;
    }
    
    public void setAvailabilityStatus(String availabilityStatus) {
        if (availabilityStatus != null && availabilityStatus.trim().isEmpty()) {
            throw new IllegalArgumentException("Availability status cannot be empty");
        }
        this.availabilityStatus = availabilityStatus;
    }
}