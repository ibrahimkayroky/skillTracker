package com.example.skillTrackr.repository;

import com.example.skillTrackr.model.GroupConversation;
import com.example.skillTrackr.model.GroupMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupMessageRepository extends JpaRepository<GroupMessage, Long> {
    List<GroupMessage> findByGroupOrderBySentAtAsc(GroupConversation group);
}