package com.spring.blog.service.impl;

import com.spring.blog.entity.Post;
import com.spring.blog.entity.User;
import com.spring.blog.entity.common.LocalDate;
import com.spring.blog.exception.ResourceNotFoundException;
import com.spring.blog.payload.request.CreatePostRequestDto;
import com.spring.blog.repository.PostRepository;
import com.spring.blog.repository.UserRepository;
import com.spring.blog.security.UserPrincipal;
import com.spring.blog.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Override
    public Post createPost(CreatePostRequestDto dto, UserPrincipal userPrincipal) {

        Post post = Post.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .date(LocalDate.builder()
                        .createdAt(LocalDateTime.now())
                        .build())
                .userId(userPrincipal.getId())
                .build();

        return postRepository.save(post);
    }

    @Override
    public Post findByPost(Long postId) {
        return null;
    }
}
