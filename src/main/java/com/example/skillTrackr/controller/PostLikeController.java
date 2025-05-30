package com.example.skillTrackr.controller;

import com.example.skillTrackr.service.PostLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/post-likes")
@RequiredArgsConstructor
public class PostLikeController {

    private final PostLikeService likeService;

    @PostMapping("/like")
    public ResponseEntity<String> likePost(@RequestParam Long userId, @RequestParam Long postId) {
        return ResponseEntity.ok(likeService.likePost(userId, postId));
    }

    @GetMapping("/count/{postId}")
    public ResponseEntity<Long> getLikeCount(@PathVariable Long postId) {
        return ResponseEntity.ok(likeService.countLikes(postId));
    }
}
