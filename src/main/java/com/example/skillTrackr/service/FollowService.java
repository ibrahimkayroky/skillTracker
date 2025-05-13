package com.example.skillTrackr.service;

import com.example.skillTrackr.model.Follow;
import com.example.skillTrackr.model.Like;
import com.example.skillTrackr.model.Skill;
import com.example.skillTrackr.model.User;
import com.example.skillTrackr.repository.FollowRepository;
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
public class FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    public String followUser(String followerEmail, Long followingId) {
        User follower = userRepository.findByEmail(followerEmail)
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));

        User following = userRepository.findById(followingId)
                .orElseThrow(() -> new EntityNotFoundException("user not found"));

        if (follower.equals(following)){
            return "u can not follow ur self.";
        }

        Optional<Follow> existingFollow = followRepository.findByFollowerAndFollowing(follower,following);
        if (existingFollow.isPresent()) {
            return "already following this user";
        }

        Follow follow = Follow.builder()
                .follower(follower)
                .following(following)
                .followedAt(LocalDateTime.now())
                .build();

        followRepository.save(follow);
        return "User followed successfully";
    }

    public String unfollowUser(String followerEmail, Long followingId) {
        User follower = userRepository.findByEmail(followerEmail)
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));

        User following = userRepository.findById(followingId)
                .orElseThrow(() -> new EntityNotFoundException("user not found"));

        Follow follow = followRepository.findByFollowerAndFollowing(follower,following)
                .orElseThrow(() -> new EntityNotFoundException("follow not found"));

        followRepository.delete(follow);
        return "unfollowed successfully.";
    }

    public List<User> getFollowers(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("user not found"));
        return followRepository.findByFollowing(user).stream()
                .map(Follow::getFollower)
                .toList();
    }

    public List<User> getFollowing(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("user not found"));
        return followRepository.findByFollower(user).stream()
                .map(Follow::getFollowing)
                .toList();
    }


}
