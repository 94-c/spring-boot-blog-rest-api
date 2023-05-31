package com.spring.blog.controller;

import com.spring.blog.entity.Post;
import com.spring.blog.payload.ApiResponse;
import com.spring.blog.payload.SuccessResponse;
import com.spring.blog.payload.response.PostResponse;
import com.spring.blog.payload.request.CreatePostRequestDto;
import com.spring.blog.payload.request.UpdatePostRequestDto;
import com.spring.blog.security.CurrentUser;
import com.spring.blog.security.UserPrincipal;
import com.spring.blog.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(value = HttpStatus.CREATED)
    public SuccessResponse<PostResponse> createPost(@Valid @RequestBody CreatePostRequestDto dto,
                                                    @CurrentUser UserPrincipal currentUser) {

        Post createPost = postService.createPost(dto, currentUser);

        return SuccessResponse.success(PostResponse.createPostResponse(createPost));
    }

    @GetMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public SuccessResponse<PostResponse> findByPost(@PathVariable(name = "id") Long postId) {

        Post findByPost = postService.findByPost(postId);

        return SuccessResponse.success(PostResponse.findByPostResponse(findByPost));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @ResponseStatus(value = HttpStatus.OK)
    public SuccessResponse<PostResponse> updatePost(@PathVariable(name = "id") Long postId,
                                                    @Valid @RequestBody UpdatePostRequestDto dto,
                                                    @CurrentUser UserPrincipal currentUser) {
        Post updatePost = postService.updatePost(postId, dto, currentUser);

        return SuccessResponse.success(PostResponse.updatePostResponse(updatePost));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable(name = "id") Long id, @CurrentUser UserPrincipal currentUser) {
        ApiResponse apiResponse = postService.deletePost(id, currentUser);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }


}
