\package com.example.demo.repository;

import com.example.demo.model.VolunteerProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface VolunteerProfileRepository 
        extends JpaRepository<VolunteerProfile, Long> {
        
    List<VolunteerProfile> findByAvailabilityStatus(String status);
    
    Optional<VolunteerProfile> findByEmail(String email);
    
    boolean existsByVolunteerId(String volunteerId);
    
    boolean existsByEmail(String email);
    
    boolean existsByPhone(String phone);
    
    Optional<VolunteerProfile> findByVolunteerId(String volunteerId);
}