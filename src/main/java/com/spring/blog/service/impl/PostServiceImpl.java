package com.spring.blog.service.impl;

import com.spring.blog.entity.Post;
import com.spring.blog.entity.Role;
import com.spring.blog.entity.User;
import com.spring.blog.entity.common.LocalDate;
import com.spring.blog.entity.common.RoleName;
import com.spring.blog.exception.BadRequestException;
import com.spring.blog.exception.ResourceNotFoundException;
import com.spring.blog.exception.UnauthorizedException;
import com.spring.blog.payload.ApiResponse;
import com.spring.blog.payload.PageResponse;
import com.spring.blog.payload.request.CreatePostRequestDto;
import com.spring.blog.payload.request.UpdatePostRequestDto;
import com.spring.blog.payload.response.PostResponse;
import com.spring.blog.repository.PostRepository;
import com.spring.blog.repository.UserRepository;
import com.spring.blog.security.UserPrincipal;
import com.spring.blog.service.PostService;
import com.spring.blog.utils.AppConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.spring.blog.utils.AppConstants.*;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Override
    @Transactional(readOnly = true)
    public PageResponse<PostResponse> findAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Post> posts = postRepository.findAll(pageable);

        List<Post> listOfPosts = posts.getContent();

        List<PostResponse> content = listOfPosts.stream().map(PostResponse::convertToPostResponse).collect(Collectors.toList());

        PageResponse<PostResponse> pageResource = new PageResponse<>();

        pageResource.setContent(content);
        pageResource.setPageNo(pageNo);
        pageResource.setPageSize(pageSize);
        pageResource.setTotalElements(posts.getTotalElements());
        pageResource.setTotalPages(posts.getTotalPages());
        pageResource.setLast(pageResource.isLast());

        return pageResource;
    }

    @Override
    public Post createPost(CreatePostRequestDto dto, UserPrincipal currentUser) {

        Post post = Post.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .date(LocalDate.builder()
                        .createdAt(LocalDateTime.now())
                        .build())
                .userId(currentUser.getId())
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

        ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "권한이 없습니다.");

        throw new UnauthorizedException(apiResponse);
    }

    @Override
    public ApiResponse deletePost(Long postId, UserPrincipal currentUser) {
        Post findByPost = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException(POST, ID, postId));

        if (findByPost.getUserId().equals(currentUser.getId())
                || currentUser.getAuthorities().contains(new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.toString()))) {
            postRepository.deleteById(postId);

            return new ApiResponse(Boolean.TRUE, "게시물이 삭제 되었습니다.");
        }

        ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "권한이 없습니다.");

        throw new UnauthorizedException(apiResponse);
    }

}
