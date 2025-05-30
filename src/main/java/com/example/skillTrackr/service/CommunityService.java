package com.example.skillTrackr.service;

import com.example.skillTrackr.dto.JoinCommunityRequest;
import com.example.skillTrackr.enums.CommunityRole;
import com.example.skillTrackr.model.*;
import com.example.skillTrackr.repository.CommunityMemberRepository;
import com.example.skillTrackr.repository.CommunityRepository;
import com.example.skillTrackr.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommunityService {

    private final CommunityRepository communityRepository;
    private final CommunityMemberRepository communityMemberRepository;
    private final UserRepository userRepository;

    public String joinCommunity(JoinCommunityRequest request){
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Community community = communityRepository.findById(request.getCommunityId())
                .orElseThrow(() -> new RuntimeException("Community not found"));

        if (communityMemberRepository.findByUserIdAndCommunityId(user.getId(), community.getId()).isPresent()){
            return "user already exists";
        }

        CommunityMember member = CommunityMember.builder()
                .user(user)
                .community(community)
                .role(CommunityRole.MEMBER)
                .joinedAt(LocalDateTime.now())
                .build();

        communityMemberRepository.save(member);
        return "user joined";
    }

    public List<CommunityMember> getMembers(Long communityId) {
        return communityMemberRepository.findByCommunityId(communityId);
    }

}
