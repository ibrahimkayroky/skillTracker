package com.example.skillTrackr.repository;

import com.example.skillTrackr.model.Badge;
import com.example.skillTrackr.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BadgeRepository extends JpaRepository<Badge,Long> {
    boolean existsByUserAndName(User user, String name);
    List<Badge> findAllByUser(User user);

}
