package com.example.skillTrackr.repository;

import com.example.skillTrackr.model.GroupConversation;
import com.example.skillTrackr.model.GroupMember;
import com.example.skillTrackr.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {
    List<GroupMember> findByGroup(GroupConversation group);
    Optional<GroupMember> findByGroupAndUser(GroupConversation group, User user);
}
