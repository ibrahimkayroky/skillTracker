package com.example.skillTrackr.controller;

import com.example.skillTrackr.model.Badge;
import com.example.skillTrackr.model.User;
import com.example.skillTrackr.repository.UserRepository;
import com.example.skillTrackr.service.BadgeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/badges")
@RequiredArgsConstructor
public class BadgeController {

    private final BadgeService badgeService;
    private final UserRepository userRepository;

    @GetMapping("/{userId}")
    public ResponseEntity<List<Badge>> getBadgesForUser(@PathVariable Long userId) {
        Optional<User> user = userRepository.findById(userId);
        List<Badge> badges = badgeService.getBadgesForUser(user.orElse(null));
        return ResponseEntity.ok(badges);
    }
}
