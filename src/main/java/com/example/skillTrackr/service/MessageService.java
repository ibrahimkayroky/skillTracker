package com.example.skillTrackr.service;

import com.example.skillTrackr.dto.MessageDTO;
import com.example.skillTrackr.model.Conversation;
import com.example.skillTrackr.model.Message;
import com.example.skillTrackr.model.User;
import com.example.skillTrackr.repository.ConversationRepository;
import com.example.skillTrackr.repository.MessageRepository;
import com.example.skillTrackr.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final ConversationRepository conversationRepository;

    public Message sendMessage(Long senderId, MessageDTO dto) {
        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new RuntimeException("Sender not found"));

        User receiver = userRepository.findById(dto.getReceiverId())
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        Message message = Message.builder()
                .sender(sender)
                .receiver(receiver)
                .content(dto.getContent())
                .sentAt(LocalDateTime.now())
                .seen(false)
                .build();

        return messageRepository.save(message);
    }

    public List<Message> getConversation(Long user1Id, Long user2Id){
        return messageRepository.findBySenderIdAndReceiverIdOrReceiverIdAndSenderId(user1Id,user2Id,user1Id,user2Id);
    }

    public List<Message> getUnreadMessages(Long userId) {
        return messageRepository.findByReceiverIdAndSeenFalse(userId);
    }

    public List<MessageDTO> getMessagesInConversation(Long conversationId) {
        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new RuntimeException("Conversation not found"));

        return messageRepository.findByConversationOrderByTimestampAsc(conversation)
                .stream()
                .map(message -> MessageDTO.builder()
                        .senderUsername(message.getSender().getEmail())
                        .content(message.getContent())
                        .timestamp(message.getSentAt())
                        .build())
                .collect(Collectors.toList());
    }

}
