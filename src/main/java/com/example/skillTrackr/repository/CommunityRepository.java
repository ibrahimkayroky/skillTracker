package com.example.skillTrackr.repository;

import com.example.skillTrackr.model.Community;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommunityRepository extends JpaRepository<Community, Long> {
    Optional<Community> findByName(String name);
}
