package com.example.skillTrackr.repository;

import com.example.skillTrackr.model.GroupConversation;
import com.example.skillTrackr.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupConversationRepository extends JpaRepository<GroupConversation ,Long> {
    List<GroupConversation> findByMembersContains(User user);

}
