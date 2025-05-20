package com.example.skillTrackr.service;

import com.example.skillTrackr.model.Notification;
import com.example.skillTrackr.model.User;
import com.example.skillTrackr.repository.NotificationRepository;
import com.example.skillTrackr.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public void sendNotification(Long userId, String message){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("user not found"));

        Notification notification = Notification.builder()
                .user(user)
                .createdAt(LocalDateTime.now())
                .message(message)
                .isRead(false)
                .build();

        notificationRepository.save(notification);
    }

    public List<Notification> getNotificationsForUser(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("user not found"));

        return notificationRepository.findByUserOrderByCreatedAtDesc(user);
    }

    public void markAsRead(Long notificationId){
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found"));

        notification.setRead(true);
        notificationRepository.save(notification);
    }

    public void deleteNotification(Long notificationId) {
        if (!notificationRepository.existsById(notificationId)) {
            throw new RuntimeException("Notification not found");
        }
        notificationRepository.deleteById(notificationId);
    }



}
