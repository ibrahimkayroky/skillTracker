package com.example.skillTrackr.dto;

import lombok.Data;

import java.util.Set;

@Data
public class CreateGroupRequest {
    private String name;
    private String description;
    private String creator;
    private Set<String> members;
}
