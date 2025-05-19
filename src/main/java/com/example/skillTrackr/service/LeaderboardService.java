package com.example.skillTrackr.service;

import com.example.skillTrackr.dto.LeaderboardDTO;
import com.example.skillTrackr.model.User;
import com.example.skillTrackr.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LeaderboardService {

    private final UserRepository userRepository;

    public List<LeaderboardDTO> getTopUsers() {
        return userRepository.findTop10ByOrderByScoreDesc()
                .stream()
                .map(user -> new LeaderboardDTO(user.getId(), user.getFullName(), user.getBio(), user.getScore()))
                .collect(Collectors.toList());
    }

}
