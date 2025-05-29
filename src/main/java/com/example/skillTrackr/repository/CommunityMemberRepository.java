package com.example.skillTrackr.repository;

import com.example.skillTrackr.model.CommunityMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommunityMemberRepository extends JpaRepository<CommunityMember, Long> {
    Optional<CommunityMember> findByUserIdAndCommunityId(Long userId, Long communityId);
    List<CommunityMember> findByCommunityId(Long communityId);
}
