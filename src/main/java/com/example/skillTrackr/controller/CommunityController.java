package com.example.skillTrackr.controller;

import com.example.skillTrackr.model.CommunityMember;
import com.example.skillTrackr.dto.JoinCommunityRequest;
import com.example.skillTrackr.service.CommunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/community")
@RequiredArgsConstructor
public class CommunityController {

    private final CommunityService communityService;

    @PostMapping("/join")
    public ResponseEntity<String> joinCommunity(@RequestBody JoinCommunityRequest request) {
        return ResponseEntity.ok(communityService.joinCommunity(request));
    }

    @GetMapping("/{communityId}/members")
    public ResponseEntity<List<CommunityMember>> getMembers(@PathVariable Long communityId) {
        return ResponseEntity.ok(communityService.getMembers(communityId));
    }
}
