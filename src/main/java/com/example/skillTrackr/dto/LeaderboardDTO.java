package com.example.skillTrackr.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LeaderboardDTO {
    private Long userId;
    private String name;
    private String title;
    private Integer score;
}
