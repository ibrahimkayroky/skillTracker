package com.example.skillTrackr.repository;

import com.example.skillTrackr.model.Endorsement;
import com.example.skillTrackr.model.Skill;
import com.example.skillTrackr.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EndorsementRepository extends JpaRepository<Endorsement, Long> {
    boolean existsByEndorserAndSkill(User endorser, Skill skill);
    Long countBySkill(Skill skill);
}
