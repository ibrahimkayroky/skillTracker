package com.example.skillTrackr.dto;

import lombok.Data;

@Data
public class SendGroupMessageRequest {
    private Long groupId;
    private String senderUsername;
    private String content;
}