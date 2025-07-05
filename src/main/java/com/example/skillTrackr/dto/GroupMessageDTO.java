package com.example.skillTrackr.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupMessageDTO {
    private String sender;
    private String content;
    private LocalDateTime sentAt;
    private Long groupId;
}

