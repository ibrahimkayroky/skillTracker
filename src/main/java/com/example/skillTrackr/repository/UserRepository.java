package com.example.skillTrackr.repository;

import com.example.skillTrackr.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    List<User> findTop10ByOrderByScoreDesc();
    Optional<User> findById(Long userId);
}
