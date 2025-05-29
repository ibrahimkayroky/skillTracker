package com.example.skillTrackr.controller;

import com.example.skillTrackr.dto.CreatedPostRequest;
import com.example.skillTrackr.model.CommunityPost;
import com.example.skillTrackr.service.CommunityPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/community-posts")
@RequiredArgsConstructor
public class CommunityPostController {

    private final CommunityPostService postService;

    @PostMapping("/create")
    public ResponseEntity<CommunityPost> createPost(@RequestBody CreatedPostRequest request) {
        return ResponseEntity.ok(postService.createPost(request));
    }

    @GetMapping("/community/{communityId}")
    public ResponseEntity<List<CommunityPost>> getPosts(@PathVariable Long communityId) {
        return ResponseEntity.ok(postService.getCommunityPosts(communityId));
    }
}
