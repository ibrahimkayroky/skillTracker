package com.example.skillTrackr.service;

import com.example.skillTrackr.model.Badge;
import com.example.skillTrackr.model.User;
import com.example.skillTrackr.repository.BadgeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BadgeService {

    private final BadgeRepository badgeRepository;
    private final NotificationService notificationService;

    public void grantBadge(User user, String name, String description) {
        if (badgeRepository.existsByUserAndName(user, name)){
            return;
        }

        Badge badge = Badge.builder()
                .name(name)
                .description(description)
                .user(user)
                .grantedAt(LocalDateTime.now())
                .build();

        badgeRepository.save(badge);

        notificationService.sendNotification(
                user.getId(), "congrats! you are earned the '"+ name +"' badge!"
        );
    }

    public List<Badge> getBadgesForUser(User user) {
        return badgeRepository.findAllByUser(user);
    }

}
