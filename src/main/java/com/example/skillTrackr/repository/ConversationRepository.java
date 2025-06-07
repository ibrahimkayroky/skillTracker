package com.example.skillTrackr.repository;

import com.example.skillTrackr.model.Conversation;
import com.example.skillTrackr.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long> {
    Optional<Conversation> findByUser1AndUser2(User user1, User user2);
    Optional<Conversation> findByUser2AndUser1(User user1, User user2);
}
