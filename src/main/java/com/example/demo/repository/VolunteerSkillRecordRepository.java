package com.example.demo.repository;

import com.example.demo.model.VolunteerSkillRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VolunteerSkillRecordRepository extends JpaRepository<VolunteerSkillRecord, Long> {
    
    List<VolunteerSkillRecord> findByVolunteerId(Long volunteerId);
    
    List<VolunteerSkillRecord> findBySkillNameAndSkillLevel(String skillName, String skillLevel);
    
    List<VolunteerSkillRecord> findBySkillName(String skillName);
}