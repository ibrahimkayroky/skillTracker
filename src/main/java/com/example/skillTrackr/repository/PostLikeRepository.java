package com.example.skillTrackr.repository;

import com.example.skillTrackr.model.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    boolean existsByUserIdAndPostId(Long userId, Long postId);
    long countByPostId(Long postId);
}
