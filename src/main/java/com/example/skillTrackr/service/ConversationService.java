package com.example.skillTrackr.service;

import com.example.skillTrackr.model.Conversation;
import com.example.skillTrackr.model.User;
import com.example.skillTrackr.repository.ConversationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConversationService {

    private final ConversationRepository conversationRepository;

    public Conversation getOrCreateConversation(User user1, User user2) {
        return conversationRepository.findByUser1AndUser2(user1, user2)
                .or(() -> conversationRepository.findByUser2AndUser1(user1, user2))
                .orElseGet(() -> {
                    Conversation conversation = Conversation.builder()
                            .user1(user1)
                            .user2(user2)
                            .build();
                    return conversationRepository.save(conversation);
                });
    }

    public List<Conversation> getUserConversations(User user) {
        return conversationRepository.findAll().stream()
                .filter(c -> c.getUser1().equals(user) || c.getUser2().equals(user))
                .collect(Collectors.toList());
    }

}
