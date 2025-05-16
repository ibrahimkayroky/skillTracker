package com.example.skillTrackr.service;

import com.example.skillTrackr.model.Like;
import com.example.skillTrackr.model.Skill;
import com.example.skillTrackr.model.User;
import com.example.skillTrackr.repository.LikeRepository;
import com.example.skillTrackr.repository.SkillRepository;
import com.example.skillTrackr.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final SkillRepository skillRepository;
    private final NotificationService notificationService;

    public String likeSkill(Long skillId, String userEmail){
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() -> new EntityNotFoundException("Skill not found"));

        if (likeRepository.existsByLikedByAndSkill(user, skill)){
            return "you already liked by this skill.";
        }

        Like like = Like.builder()
                .likedBy(user)
                .skill(skill)
                .likedAt(LocalDateTime.now())
                .build();

        notificationService.sendNotification(
                skill.getUser().getId(), userEmail + " liked your skill: " + skill.getName()
        );

        likeRepository.save(like);
        return "Skill liked Successfully";
    }

    public String unLikeSkill(Long skillId, String userEmail)
    {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() -> new EntityNotFoundException("Skill not found"));

        Optional<Like> like = likeRepository.findByLikedByAndSkill(user, skill);

        if (like.isEmpty()) {
            return "u have not liked this skill yet";
        }

        likeRepository.delete(like.get());
        return "Skill unLiked successfully";
    }

    public int countLikes(Long skillId){
        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() -> new RuntimeException("skill not found"));
        return likeRepository.findAllBySkill(skill).size();
    }


}
