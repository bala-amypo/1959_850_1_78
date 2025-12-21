package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.model.VolunteerSkillRecord;
import java.util.List;

@Repository
public interface VolunteerSkillRecordRepository extends JpaRepository<VolunteerSkillRecord, Long> {
    List<VolunteerSkillRecord> findByVolunteerId(Long volunteerId);
}
