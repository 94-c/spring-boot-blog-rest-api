package com.spring.blog.service.impl;

import com.spring.blog.entity.*;
import com.spring.blog.entity.common.LocalDate;
import com.spring.blog.entity.common.RoleName;
import com.spring.blog.exception.ResourceNotFoundException;
import com.spring.blog.exception.UnauthorizedException;
import com.spring.blog.payload.ApiResponse;
import com.spring.blog.payload.PageResponse;
import com.spring.blog.payload.request.PostRequestDto;
import com.spring.blog.payload.response.PostResponse;
import com.spring.blog.repository.CategoryRepository;
import com.spring.blog.repository.PostRepository;
import com.spring.blog.repository.TagRepository;
import com.spring.blog.repository.UserRepository;
import com.spring.blog.security.UserPrincipal;
import com.spring.blog.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.spring.blog.utils.AppConstants.*;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;

    @Override
    @Transactional(readOnly = true)
    public PageResponse<PostResponse> findAllPosts(int pageNo, int pageSize, String sortBy, String sortDir, String title, String content) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Post> posts = postRepository.findAllSearch(title, content, pageable);

        List<Post> listOfPosts = posts.getContent();

        List<PostResponse> postResponses = listOfPosts.stream().map(PostResponse::convertToPostResponse).collect(Collectors.toList());

        PageResponse<PostResponse> pageResource = new PageResponse<>();

        pageResource.setContent(postResponses);
        pageResource.setPageNo(pageNo);
        pageResource.setPageSize(pageSize);
        pageResource.setTotalElements(posts.getTotalElements());
        pageResource.setTotalPages(posts.getTotalPages());
        pageResource.setLast(pageResource.isLast());

        return pageResource;
    }

    @Override
    public PostResponse createPost(PostRequestDto dto, UserPrincipal currentUser) {
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException(CATEGORY, ID, dto.getCategoryId()));

        /*List<Tag> tags = new ArrayList<>(dto.getTags().size());

        //게시글 등록 시, 태그 기능 등록
        for (String name : dto.getTags()) {
            Tag tag = tagRepository.findByName(name);
            tag = tag == null ? tagRepository.save(new Tag(name)) : tag;

            tags.add(tag);
        }*/

        if (category != null) {
            Post post = Post.builder()
                    .title(dto.getTitle())
                    .content(dto.getContent())
                    .date(LocalDate.builder()
                            .createdAt(LocalDateTime.now())
                            .build())
                    .userId(currentUser.getId())
                    .category(category)
                    //.tags(tags)
                    .isEnable(0)
                    .build();

            Post createPost = postRepository.save(post);
            return PostResponse.createPostResponse(createPost);
        }

        Post post = Post.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .date(LocalDate.builder()
                        .createdAt(LocalDateTime.now())
                        .build())
                .userId(currentUser.getId())
                //.tags(tags)
                .isEnable(0)
                .build();

        Post createPost = postRepository.save(post);
        return PostResponse.createPostResponse(createPost);
    }

    @Override
    public Post findByPost(Long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException(POST, ID, postId));
    }

    @Override
    public Post updatePost(Long postId, PostRequestDto dto, UserPrincipal currentUser) {
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException(CATEGORY, ID, dto.getCategoryId()));

        Post findByPost = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException(POST, ID, postId));

        if (findByPost.getUserId().equals(currentUser.getId())
                || currentUser.getAuthorities().contains(new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.toString()))) {
            findByPost.setTitle(dto.getTitle());
            findByPost.setContent(dto.getContent());
            findByPost.setDate(LocalDate.builder()
                    .updateAt(LocalDateTime.now())
                    .build());
            findByPost.setCategory(category);
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

    @Override
    public Post isEnable(Long postId,UserPrincipal currentUser) {
        Post findByPost = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException(POST, ID, postId));

        if (findByPost.getUserId().equals(currentUser.getId())
                || currentUser.getAuthorities().contains(new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.toString()))) {
            findByPost.setIsEnable(0);
            findByPost.setDate(LocalDate.builder()
                    .updateAt(LocalDateTime.now())
                    .build());
            return postRepository.save(findByPost);
        }

        ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "권한이 없습니다.");

        throw new UnauthorizedException(apiResponse);
    }

    @Override
    public Post isUnable(Long postId, UserPrincipal currentUser) {
        Post findByPost = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException(POST, ID, postId));

        if (findByPost.getUserId().equals(currentUser.getId())
                || currentUser.getAuthorities().contains(new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.toString()))) {
            findByPost.setIsEnable(1);
            findByPost.setDate(LocalDate.builder()
                    .updateAt(LocalDateTime.now())
                    .build());
            return postRepository.save(findByPost);
        }

        ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "권한이 없습니다.");

        throw new UnauthorizedException(apiResponse);
    }

}
