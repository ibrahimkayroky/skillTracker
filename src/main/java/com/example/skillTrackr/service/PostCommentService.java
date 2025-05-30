package com.example.skillTrackr.service;

import com.example.skillTrackr.dto.CreateCommentRequest;
import com.example.skillTrackr.model.CommunityPost;
import com.example.skillTrackr.model.PostComment;
import com.example.skillTrackr.model.User;
import com.example.skillTrackr.repository.CommunityPostRepository;
import com.example.skillTrackr.repository.PostCommentRepository;
import com.example.skillTrackr.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostCommentService {

    private final PostCommentRepository commentRepository;
    private final CommunityPostRepository postRepository;
    private final UserRepository userRepository;

    public PostComment addComment(CreateCommentRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        CommunityPost post = postRepository.findById(request.getPostId())
                .orElseThrow(() -> new RuntimeException("Post not found"));

        PostComment comment = PostComment.builder()
                .user(user)
                .post(post)
                .content(request.getContent())
                .commentedAt(LocalDateTime.now())
                .build();

        return commentRepository.save(comment);
    }

    public List<PostComment> getCommentsForPost(Long postId) {
        return commentRepository.findByPostIdOrderByCommentedAtDesc(postId);
    }
}
