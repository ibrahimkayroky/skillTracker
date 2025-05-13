package com.example.skillTrackr.controller;

import com.example.skillTrackr.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/skills")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/{skillId}/like")
    public ResponseEntity<String> likeSkill
            (@PathVariable Long skillId,
             Principal principal){
        String message = likeService.likeSkill(skillId, principal.getName());
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/{skillId}/unlike")
    public ResponseEntity<String> unlikeSkill(
            @PathVariable Long skillId,
            Principal principal
    ){
        String message = likeService.unLikeSkill(skillId, principal.getName());
        return ResponseEntity.ok(message);
    }

    @GetMapping("/{skillId}/count")
    public int getLikes(@RequestParam Long skillId){
        return likeService.countLikes(skillId);
    }
}
