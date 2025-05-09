package com.example.skillTrackr.controller;

import com.example.skillTrackr.dto.CommentDTO;
import com.example.skillTrackr.model.Comment;
import com.example.skillTrackr.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/skills")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{skillId}/comments")
    public ResponseEntity<String> addComment(
            @PathVariable Long skillId,
            @RequestBody CommentDTO commentDTO,
            Principal principal
            ){
        String message = commentService.addComment(skillId, principal.getName(), commentDTO);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/{skillId}/comments")
    public ResponseEntity<List<Comment>> getComments(@PathVariable Long skillId){
        List<Comment> comments = commentService.getCommentsBySkill(skillId);
        return ResponseEntity.ok(comments);
    }

}
