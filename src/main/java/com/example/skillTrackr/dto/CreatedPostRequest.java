package com.example.skillTrackr.dto;

import lombok.Data;

@Data
public class CreatedPostRequest {

    private Long authorId;
    private Long communityId;
    private String content;
}
