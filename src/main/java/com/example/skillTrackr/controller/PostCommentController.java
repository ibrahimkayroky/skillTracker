package com.example.skillTrackr.controller;

import com.example.skillTrackr.dto.CreateCommentRequest;
import com.example.skillTrackr.model.PostComment;
import com.example.skillTrackr.service.PostCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post-comments")
@RequiredArgsConstructor
public class PostCommentController {

    private final PostCommentService commentService;

    @PostMapping("/add")
    public ResponseEntity<PostComment> addComment(@RequestBody CreateCommentRequest request) {
        return ResponseEntity.ok(commentService.addComment(request));
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<PostComment>> getComments(@PathVariable Long postId) {
        return ResponseEntity.ok(commentService.getCommentsForPost(postId));
    }
}
