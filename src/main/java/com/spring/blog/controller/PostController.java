package com.spring.blog.controller;

import com.spring.blog.entity.Post;
import com.spring.blog.payload.SuccessResponse;
import com.spring.blog.payload.dto.PostDto;
import com.spring.blog.payload.request.CreatePostRequestDto;
import com.spring.blog.payload.response.CreatePostResponseDto;
import com.spring.blog.security.CurrentUser;
import com.spring.blog.security.UserPrincipal;
import com.spring.blog.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(value = HttpStatus.CREATED)
    public SuccessResponse<CreatePostResponseDto> createPost(@Valid @RequestBody CreatePostRequestDto dto,
                                                             @CurrentUser UserPrincipal currentUser) {

        Post createPost = postService.createPost(dto, currentUser);

        return SuccessResponse.success(CreatePostResponseDto.builder()
                .id(createPost.getId())
                .userId(createPost.getUserId())
                .title(createPost.getTitle())
                .content(createPost.getContent())
                .createdAt(createPost.getDate().getCreatedAt())
                .build());
    }

    @GetMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public SuccessResponse<PostDto> findByPost(@PathVariable(name = "id") Long postId) {

        Post findByPost = postService.findByPost(postId);

        return SuccessResponse.success(PostDto.builder()
                .id(findByPost.getId())
                .userId(findByPost.getUserId())
                .title(findByPost.getTitle())
                .content(findByPost.getContent())
                .createdAt(findByPost.getDate().getCreatedAt())
                .updatedAt(findByPost.getDate().getUpdateAt())
                .build());
    }


}
