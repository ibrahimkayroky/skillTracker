package com.example.skillTrackr.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConversationDTO {
    private Long conversationId;
    private String otherUsername;
    private String lastMessage;
    private LocalDateTime lastMessageTime;
}
