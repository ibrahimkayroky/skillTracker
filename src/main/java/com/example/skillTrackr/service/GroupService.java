package com.example.skillTrackr.service;

import com.example.skillTrackr.model.GroupConversation;
import com.example.skillTrackr.model.User;
import com.example.skillTrackr.repository.GroupConversationRepository;
import com.example.skillTrackr.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupConversationRepository groupConversationRepository;
    private final UserRepository userRepository;

    public GroupConversation createGroup(String name, String desc, String creatorUsername, Set<String> membernames) {
        User creator = userRepository.findByEmail(creatorUsername).orElseThrow();
        Set<User> members = membernames.stream()
                .map(u -> userRepository.findByEmail(u).orElseThrow())
                .collect(Collectors.toSet());
        members.add(creator);

        GroupConversation groupConversation = GroupConversation.builder()
                .name(name)
                .description(desc)
                .creator(creator)
                .members(members)
                .build();

        return groupConversationRepository.save(groupConversation);
    }

}
