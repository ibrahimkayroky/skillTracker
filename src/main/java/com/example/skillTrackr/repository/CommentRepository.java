package com.example.skillTrackr.repository;

import com.example.skillTrackr.model.Comment;
import com.example.skillTrackr.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findBySkill(Skill skill);
}
