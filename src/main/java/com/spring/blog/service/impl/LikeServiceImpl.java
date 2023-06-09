package com.spring.blog.service.impl;

import com.spring.blog.entity.Like;
import com.spring.blog.entity.Post;
import com.spring.blog.entity.User;
import com.spring.blog.entity.common.LocalDate;
import com.spring.blog.exception.ResourceNotFoundException;
import com.spring.blog.repository.LikeRepository;
import com.spring.blog.repository.PostRepository;
import com.spring.blog.repository.UserRepository;
import com.spring.blog.security.UserPrincipal;
import com.spring.blog.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static com.spring.blog.utils.AppConstants.*;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {
    private final PostRepository postRepository;
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;

    @Override
    public boolean hasLikePost(Post post, Long userId) {
        return likeRepository.findByPostAndUserId(post, userId).isPresent();
    }

    public Like createLikePost(Post post, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(USER, ID, 1L));

        Like like = Like.builder()
                .post(post)
                .userId(user.getId())
                .date(LocalDate.builder()
                        .createdAt(LocalDateTime.now())
                        .build())
                .build();

        return likeRepository.save(like);
    }

    @Override
    public void deleteLikePost(Post post, Long userId) {
        Like likePost = likeRepository.findByPostAndUserId(post, userId)
                .orElseThrow(() -> new ResourceNotFoundException(LIKE, ID, 1L));

        likeRepository.delete(likePost);
    }

    @Override
    @Transactional
    public Like updateLikeOfPost(Long postId, UserPrincipal currentUser) {
        User user = userRepository.findById(currentUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException(USER, ID, 1L));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException(POST, ID, postId));

        if (!hasLikePost(post, user.getId())) {
            post.increaseLikeCount();
            return createLikePost(post, currentUser.getId());
        }

        post.decreaseLikeCount();
        deleteLikePost(post, currentUser.getId());
        return null;
    }
}
