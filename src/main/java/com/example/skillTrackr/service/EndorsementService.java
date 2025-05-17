package com.example.skillTrackr.service;

import com.example.skillTrackr.model.Endorsement;
import com.example.skillTrackr.model.Skill;
import com.example.skillTrackr.model.User;
import com.example.skillTrackr.repository.EndorsementRepository;
import com.example.skillTrackr.repository.SkillRepository;
import com.example.skillTrackr.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EndorsementService {

    private final EndorsementRepository endorsementRepository;
    private final SkillRepository skillRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    public void endorseSkill(Long skillId, Long endorserId) {
        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() -> new RuntimeException("skill not found"));

        User endorser = userRepository.findById(endorserId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (skill.getUser().getId().equals(endorserId)) {
            throw new RuntimeException("You cannot endorse your own skill");
        }

        if(endorsementRepository.existsByEndorserAndSkill(endorser, skill)) {
            throw new RuntimeException("You already endorsed this skill");
        }

        Endorsement endorsement = Endorsement.builder()
                .endorser(endorser)
                .skill(skill)
                .endorsedAt(LocalDateTime.now())
                .build();

        endorsementRepository.save(endorsement);

        notificationService.sendNotification(
                skill.getUser().getId(),
                endorser.getEmail() + " endorsed your skill: " + skill.getName()
        );
    }

    public Long getEndorsementCount(Long skillId) {
        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() -> new RuntimeException("skill not found"));
        return endorsementRepository.countBySkill(skill);
    }


}
