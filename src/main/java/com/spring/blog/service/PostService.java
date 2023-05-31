package com.spring.blog.service;

import com.spring.blog.entity.Post;
import com.spring.blog.payload.request.CreatePostRequestDto;
import com.spring.blog.security.UserPrincipal;

public interface PostService {
    Post createPost(CreatePostRequestDto dto, UserPrincipal currentUser);

    Post findByPost(Long postId);

}
