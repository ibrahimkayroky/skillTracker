package com.example.skillTrackr.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageDTO {
    private String senderUsername;
    private Long receiverId;
    private String content;
    private LocalDateTime timestamp;

}
