package com.example.demo.service.impl;

import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.VolunteerSkillRecord;
import com.example.demo.repository.VolunteerSkillRecordRepository;
import com.example.demo.service.VolunteerSkillService;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class VolunteerSkillServiceImpl implements VolunteerSkillService {
    
    private final VolunteerSkillRecordRepository volunteerSkillRecordRepository;
    
    public VolunteerSkillServiceImpl(VolunteerSkillRecordRepository volunteerSkillRecordRepository) {
        this.volunteerSkillRecordRepository = volunteerSkillRecordRepository;
    }
    
    @Override
    public List<VolunteerSkillRecord> getSkillsByVolunteer(Long volunteerId) {
        return volunteerSkillRecordRepository.findByVolunteerId(volunteerId);
    }
    
    @Override
    public VolunteerSkillRecord addSkill(VolunteerSkillRecord skill) {
        if (skill.getSkillName() == null || skill.getSkillName().trim().isEmpty()) {
            throw new BadRequestException("Skill name must not be null or blank");
        }
        skill.setUpdatedAt(LocalDateTime.now());
        return volunteerSkillRecordRepository.save(skill);
    }
    
    @Override
    public VolunteerSkillRecord updateSkill(Long skillId, VolunteerSkillRecord skill) {
        VolunteerSkillRecord existingSkill = volunteerSkillRecordRepository.findById(skillId)
            .orElseThrow(() -> new ResourceNotFoundException("Skill not found"));
        
        if (skill.getSkillName() != null && !skill.getSkillName().trim().isEmpty()) {
            existingSkill.setSkillName(skill.getSkillName());
        }
        if (skill.getSkillLevel() != null) {
            existingSkill.setSkillLevel(skill.getSkillLevel());
        }
        existingSkill.setCertified(skill.isCertified());
        existingSkill.setUpdatedAt(LocalDateTime.now());
        
        return volunteerSkillRecordRepository.save(existingSkill);
    }
    
    public VolunteerSkillRecord addOrUpdateSkill(VolunteerSkillRecord skill) {
        skill.setUpdatedAt(LocalDateTime.now());
        return volunteerSkillRecordRepository.save(skill);
    }
}