package com.example.skillTrackr.repository;

import com.example.skillTrackr.model.Like;
import com.example.skillTrackr.model.Skill;
import com.example.skillTrackr.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    boolean existsByLikedByAndSkill(User user, Skill skill);
    Optional<Like> findByLikedByAndSkill(User user, Skill skill);
    List<Like> findAllBySkill(Skill skill);
}
