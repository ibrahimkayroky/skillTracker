package com.example.skillTrackr.controller;

import com.example.skillTrackr.dto.ConversationDTO;
import com.example.skillTrackr.model.Conversation;
import com.example.skillTrackr.model.Message;
import com.example.skillTrackr.model.User;
import com.example.skillTrackr.repository.MessageRepository;
import com.example.skillTrackr.repository.UserRepository;
import com.example.skillTrackr.service.ConversationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/conversations")
@RequiredArgsConstructor
public class ConversationController {

    private final ConversationService conversationService;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<ConversationDTO>> getUserConversations(@RequestParam String username) {
        User user = userRepository.findByEmail(username).orElseThrow();

        List<Conversation> conversations = conversationService.getUserConversations(user);

        List<ConversationDTO> result = conversations.stream().map(convo -> {
            User other = convo.getUser1().equals(user) ? convo.getUser2() : convo.getUser1();

            Message lastMsg = messageRepository
                    .findByConversationOrderByTimestampAsc(convo)
                    .stream()
                    .reduce((first, second) -> second) // Get last message
                    .orElse(null);

            return ConversationDTO.builder()
                    .conversationId(convo.getId())
                    .otherUsername(other.getEmail())
                    .lastMessage(lastMsg != null ? lastMsg.getContent() : "")
                    .lastMessageTime(lastMsg != null ? lastMsg.getSentAt() : null)
                    .build();
        }).toList();

        return ResponseEntity.ok(result);
    }
}
