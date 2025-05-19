package com.example.skillTrackr.controller;

import com.example.skillTrackr.dto.LeaderboardDTO;
import com.example.skillTrackr.model.User;
import com.example.skillTrackr.service.LeaderboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/leaderboard")
@RequiredArgsConstructor
public class LeaderboardController {

    private final LeaderboardService leaderboardService;

    @GetMapping
    public ResponseEntity<List<LeaderboardDTO>> getLeaderBoard() {
        return ResponseEntity.ok(leaderboardService.getTopUsers());
    }

}
