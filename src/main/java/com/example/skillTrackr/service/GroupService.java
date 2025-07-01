package com.example.skillTrackr.service;

import com.example.skillTrackr.dto.SendGroupMessageRequest;
import com.example.skillTrackr.enums.GroupPrivacy;
import com.example.skillTrackr.enums.GroupRole;
import com.example.skillTrackr.model.GroupConversation;
import com.example.skillTrackr.model.GroupMember;
import com.example.skillTrackr.model.GroupMessage;
import com.example.skillTrackr.model.User;
import com.example.skillTrackr.repository.GroupConversationRepository;
import com.example.skillTrackr.repository.GroupMemberRepository;
import com.example.skillTrackr.repository.GroupMessageRepository;
import com.example.skillTrackr.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupMemberRepository groupMemberRepository;
    private final GroupConversationRepository groupConversationRepository;
    private final UserRepository userRepository;
    private final GroupMessageRepository groupMessageRepository;

    public GroupConversation createGroup(String name, String desc, String creatorUsername, Set<String> membernames) {
        User creator = userRepository.findByEmail(creatorUsername).orElseThrow();
        Set<User> members = membernames.stream()
                .map(u -> userRepository.findByEmail(u).orElseThrow())
                .collect(Collectors.toSet());
        members.add(creator);

        GroupConversation groupConversation = GroupConversation.builder()
                .name(name)
                .description(desc)
                .privacy(GroupPrivacy.PUBLIC)
                .creator(creator)
                .build();

        GroupMember creatorMember = GroupMember.builder()
                .group(groupConversation)
                .user(creator)
                .role(GroupRole.CREATOR)
                .build();
        groupMemberRepository.save(creatorMember);

        for (String username : membernames) {
            if (!username.equals(creatorUsername)) {
                User user = userRepository.findByEmail(username).orElseThrow();
                GroupMember member = GroupMember.builder()
                        .group(groupConversation)
                        .user(user)
                        .role(GroupRole.MEMBER)
                        .build();
                groupMemberRepository.save(member);
            }
        }
        return groupConversationRepository.save(groupConversation);
    }

    public GroupMessage sendGroupMessage(SendGroupMessageRequest request) {
        GroupConversation group = groupConversationRepository.findById(request.getGroupId())
                .orElseThrow(() -> new RuntimeException("Group not found"));

        User sender = userRepository.findByEmail(request.getSenderUsername())
                .orElseThrow(() -> new RuntimeException("Sender not found"));

        if (!group.getMembers().contains(sender)) {
            throw new RuntimeException("User not in group");
        }

        GroupMessage message = GroupMessage.builder()
                .group(group)
                .sender(sender)
                .content(request.getContent())
                .build();

        return groupMessageRepository.save(message);
    }

    public List<GroupConversation> getGroupsOfUser(String username){
        User user = userRepository.findByEmail(username).orElseThrow();
        return groupConversationRepository.findByMembersContains(user);
    }

    public List<GroupConversation> getPublicGroups() {
        return groupConversationRepository.findByPrivacy(GroupPrivacy.PUBLIC);
    }

    public GroupConversation joinGroup(Long groupId, String username) {
        GroupConversation groupConversation = groupConversationRepository.findById(groupId).orElseThrow();
        User user = userRepository.findByEmail(username).orElseThrow();
        groupConversation.getMembers();


//        groupConversation.getMembers().add(user);
        return groupConversationRepository.save(groupConversation);
    }

    public boolean isAdminOrCreator(User user, GroupConversation group) {
        return groupMemberRepository.findByGroupAndUser(group, user)
                .map(member -> member.getRole() == GroupRole.ADMIN || member.getRole() == GroupRole.CREATOR)
                .orElse(false);
    }

}
