package com.spring.blog.service;

import com.spring.blog.entity.Post;
import com.spring.blog.payload.ApiResponse;
import com.spring.blog.payload.PageResponse;
import com.spring.blog.payload.request.PostRequestDto;
import com.spring.blog.payload.response.PostResponse;
import com.spring.blog.security.UserPrincipal;

public interface PostService {

    PageResponse<PostResponse> findAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

    Post createPost(PostRequestDto dto, UserPrincipal currentUser);

    Post findByPost(Long postId);

    Post updatePost(Long postId, PostRequestDto dto, UserPrincipal currentUser);

    ApiResponse deletePost(Long id, UserPrincipal currentUser);
}
