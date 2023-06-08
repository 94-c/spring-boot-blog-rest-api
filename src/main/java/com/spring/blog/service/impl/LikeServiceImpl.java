package com.spring.blog.service.impl;

import com.spring.blog.entity.Like;
import com.spring.blog.entity.Post;
import com.spring.blog.repository.LikeRepository;
import com.spring.blog.repository.PostRepository;
import com.spring.blog.repository.UserRepository;
import com.spring.blog.security.UserPrincipal;
import com.spring.blog.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {
    private final PostRepository postRepository;
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;

    @Override
    public boolean hasLikePost(Post post, UserPrincipal currentUser) {
        return likeRepository.findByPostAndUserId(post, currentUser.getId()).isPresent();
    }

    @Override
    public Like createLikePost(Post post, UserPrincipal currentUser) {
        return null;
    }

    @Override
    public void deleteLikePost(Post post, UserPrincipal currentUser) {

    }

    @Override
    public Like updateLikeOfPost(Long postId, String email) {
        return null;
    }
}
