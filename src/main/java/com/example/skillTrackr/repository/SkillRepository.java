package com.example.skillTrackr.repository;

import com.example.skillTrackr.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillRepository extends JpaRepository<Skill, Long> {
}
