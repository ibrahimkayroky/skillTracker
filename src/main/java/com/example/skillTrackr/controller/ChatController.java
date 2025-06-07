package com.example.skillTrackr.controller;

import com.example.skillTrackr.model.ChatMessage;
import com.example.skillTrackr.model.Conversation;
import com.example.skillTrackr.model.Message;
import com.example.skillTrackr.model.User;
import com.example.skillTrackr.repository.MessageRepository;
import com.example.skillTrackr.repository.UserRepository;
import com.example.skillTrackr.service.ConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
public class ChatController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConversationService conversationService;

    @Autowired
    private MessageRepository messageRepository;

    @MessageMapping("/chat")
    public void sendMessage(ChatMessage message) {
        User sender = userRepository.findByEmail(message.getSender()).orElseThrow();
        User recipient = userRepository.findByEmail(message.getRecipient()).orElseThrow();

        Conversation conversation = conversationService.getOrCreateConversation(sender, recipient);

        Message msg = Message.builder()
                .sender(sender)
                .receiver(recipient)
                .content(message.getContent())
                .conversation(conversation)
                .build();

        messageRepository.save(msg);

        messagingTemplate.convertAndSendToUser(
                recipient.getEmail(),
                "/queue/messages",
                message
        );
    }

}
