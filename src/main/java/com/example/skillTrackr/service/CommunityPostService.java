package com.example.skillTrackr.service;

import com.example.skillTrackr.dto.CreatedPostRequest;
import com.example.skillTrackr.model.Community;
import com.example.skillTrackr.model.CommunityPost;
import com.example.skillTrackr.model.User;
import com.example.skillTrackr.repository.CommunityPostRepository;
import com.example.skillTrackr.repository.CommunityRepository;
import com.example.skillTrackr.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommunityPostService {

    private final CommunityPostRepository postRepository;
    private final UserRepository userRepository;
    private final CommunityRepository communityRepository;

    public CommunityPost createPost(CreatedPostRequest request) {
        User user = userRepository.findById(request.getAuthorId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Community community = communityRepository.findById(request.getCommunityId())
                .orElseThrow(() -> new RuntimeException("Community not found"));

        CommunityPost post = CommunityPost.builder()
                .author(user)
                .community(community)
                .content(request.getContent())
                .createdAt(LocalDateTime.now())
                .build();

        return postRepository.save(post);
    }

    public List<CommunityPost> getCommunityPosts(Long communityId) {
        return postRepository.findByCommunityIdOrderByCreatedAtDesc(communityId);
    }
}
