package com.example.demo.dto;

public class AvailabilityUpdateRequest {
    private Long volunteerId;
    private String availabilityStatus;
    
    public AvailabilityUpdateRequest() {}
    
    public AvailabilityUpdateRequest(Long volunteerId, String availabilityStatus) {
        this.volunteerId = volunteerId;
        this.availabilityStatus = availabilityStatus;
    }
    
    public Long getVolunteerId() { return volunteerId; }
    public void setVolunteerId(Long volunteerId) { this.volunteerId = volunteerId; }
    
    public String getAvailabilityStatus() { return availabilityStatus; }
    public void setAvailabilityStatus(String availabilityStatus) { this.availabilityStatus = availabilityStatus; }
}