package com.example.skillTrackr.repository;

import com.example.skillTrackr.model.Notification;
import com.example.skillTrackr.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification,Long> {
    List<Notification> findByUserOrderByCreatedAtDesc(User user);
}
