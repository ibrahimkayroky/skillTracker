package com.example.skillTrackr.service;

import com.example.skillTrackr.model.CommunityPost;
import com.example.skillTrackr.model.PostLike;
import com.example.skillTrackr.model.User;
import com.example.skillTrackr.repository.CommunityPostRepository;
import com.example.skillTrackr.repository.PostLikeRepository;
import com.example.skillTrackr.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;
    private final CommunityPostRepository communityPostRepository;
    private final UserRepository userRepository;

    public String likePost(Long userId, Long postId) {
        if (postLikeRepository.existsByUserIdAndPostId(userId, postId)){
            return "already liked";
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        CommunityPost post = communityPostRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        PostLike like = PostLike.builder()
                .user(user)
                .post(post)
                .likedAt(LocalDateTime.now())
                .build();

        postLikeRepository.save(like);
        return "liked successfully";
    }

    public long countLikes(Long postId) {
        return postLikeRepository.countByPostId(postId);
    }
}
