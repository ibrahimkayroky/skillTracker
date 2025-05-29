package com.example.skillTrackr.repository;

import com.example.skillTrackr.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findBySenderIdAndReceiverIdOrReceiverIdAndSenderId(
            Long senderId, Long receiverId,Long senderId2, Long receiverId2
    );

    List<Message> findByReceiverIdAndSeenFalse(Long receiverId);
}
