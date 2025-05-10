package com.example.skillTrackr.repository;

import com.example.skillTrackr.model.Follow;
import com.example.skillTrackr.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<Follow,Long> {
    Optional<Follow> findByFollowerAndFollowing(User follower, User following);
    List<Follow> findByFollower(User user);
    List<Follow> findByFollowing(User user);
}
