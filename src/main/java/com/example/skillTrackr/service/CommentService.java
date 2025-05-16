package com.example.skillTrackr.service;

import com.example.skillTrackr.dto.CommentDTO;
import com.example.skillTrackr.model.Comment;
import com.example.skillTrackr.model.Skill;
import com.example.skillTrackr.model.User;
import com.example.skillTrackr.repository.CommentRepository;
import com.example.skillTrackr.repository.SkillRepository;
import com.example.skillTrackr.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final SkillRepository skillRepository;
    private final NotificationService notificationService;

    public String addComment(Long skillId, String userEmail, CommentDTO commentDTO){
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() -> new EntityNotFoundException("Skill not found"));

        Comment comment = Comment.builder()
                .content(commentDTO.getContent())
                .commentedBy(user)
                .skill(skill)
                .commentedAt(LocalDateTime.now())
                .build();

        notificationService.sendNotification(
                skill.getUser().getId(), userEmail + " commented on your skill: " + commentDTO.getContent()
        );

        commentRepository.save(comment);
        return "comment added";
    }

    public List<Comment> getCommentsBySkill(Long skillId) {
        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() -> new EntityNotFoundException("Skill now found"));
        return commentRepository.findBySkill(skill);
    }
}
