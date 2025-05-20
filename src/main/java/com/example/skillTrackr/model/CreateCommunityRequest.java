package com.example.skillTrackr.model;

import lombok.Data;

@Data
public class CreateCommunityRequest {
    private String name;
    private String description;
    private Boolean isPrivate;
}
