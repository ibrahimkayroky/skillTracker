package com.example.skillTrackr.dto;

import lombok.Data;

@Data
public class JoinCommunityRequest {

    private Long communityId;
    private Long userId;
}
