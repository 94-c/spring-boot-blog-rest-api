package com.spring.blog.service.impl;

import com.spring.blog.entity.Post;
import com.spring.blog.entity.Role;
import com.spring.blog.entity.User;
import com.spring.blog.entity.common.LocalDate;
import com.spring.blog.entity.common.RoleName;
import com.spring.blog.exception.ResourceNotFoundException;
import com.spring.blog.exception.UnauthorizedException;
import com.spring.blog.payload.ApiResponse;
import com.spring.blog.payload.request.CreatePostRequestDto;
import com.spring.blog.payload.request.UpdatePostRequestDto;
import com.spring.blog.repository.PostRepository;
import com.spring.blog.repository.UserRepository;
import com.spring.blog.security.UserPrincipal;
import com.spring.blog.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.spring.blog.utils.AppConstants.ID;
import static com.spring.blog.utils.AppConstants.POST;

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

        Post createPost = postRepository.save(post);

        return createPost;
    }

    @Override
    public Post findByPost(Long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException(POST, ID, postId));
    }

    @Override
    public Post updatePost(Long postId, UpdatePostRequestDto dto, UserPrincipal currentUser) {
        Post findByPost = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException(POST, ID, postId));

        if (findByPost.getUserId().equals(currentUser.getId())
                || currentUser.getAuthorities().contains(new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.toString()))) {
            findByPost.setTitle(dto.getTitle());
            findByPost.setContent(dto.getContent());
            return postRepository.save(findByPost);
        }

        ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "You don't have permission to edit this post");

        throw new UnauthorizedException(apiResponse);
    }


}
