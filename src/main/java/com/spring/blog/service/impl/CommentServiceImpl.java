package com.spring.blog.service.impl;

import com.spring.blog.entity.Comment;
import com.spring.blog.entity.Post;
import com.spring.blog.entity.common.LocalDate;
import com.spring.blog.entity.common.RoleName;
import com.spring.blog.exception.ResourceNotFoundException;
import com.spring.blog.exception.UnauthorizedException;
import com.spring.blog.payload.ApiResponse;
import com.spring.blog.payload.PageResponse;
import com.spring.blog.payload.request.CommentRequestDto;
import com.spring.blog.payload.response.CommentResponse;
import com.spring.blog.repository.CommentRepository;
import com.spring.blog.repository.PostRepository;
import com.spring.blog.security.UserPrincipal;
import com.spring.blog.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.spring.blog.utils.AppConstants.*;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final PostRepository postRepository;

    private final CommentRepository commentRepository;


    @Override
    @Transactional(readOnly = true)
    public PageResponse<CommentResponse> findAllComments(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Comment> comments = commentRepository.findByIsEnableTrue(pageable);

        List<Comment> listOfComments = comments.getContent();

        List<CommentResponse> content = listOfComments.stream().map(CommentResponse::convertToCommentResponse).collect(Collectors.toList());

        PageResponse<CommentResponse> pageResource = new PageResponse<>();

        pageResource.setContent(content);
        pageResource.setPageNo(pageNo);
        pageResource.setPageSize(pageSize);
        pageResource.setTotalElements(comments.getTotalElements());
        pageResource.setTotalPages(comments.getTotalPages());
        pageResource.setLast(pageResource.isLast());

        return pageResource;
    }

    @Override
    public CommentResponse createComment(Long postId, CommentRequestDto dto, UserPrincipal currentUser) {
        Post findByPost = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException(POST, ID, postId));

        Comment comment = Comment.builder()
                .content(dto.getContent())
                .date(LocalDate.builder()
                        .createdAt(LocalDateTime.now())
                        .build())
                .userId(currentUser.getId())
                .isEnable(1)
                .post(findByPost)
                .build();

        Comment createComment = commentRepository.save(comment);

        return CommentResponse.convertToCommentResponse(createComment);
    }

    @Override
    public Comment findByComment(Long postId, Long commentId) {
        postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException(POST, ID, postId));

        return commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException(COMMENT, ID, commentId));
    }

    @Override
    public Comment updateComment(Long postId, Long commentId, CommentRequestDto dto, UserPrincipal currentUser) {
        Post findByPost = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException(POST, ID, postId));

        Comment findByComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException(COMMENT, ID, commentId));

        if (findByComment.getUserId().equals(currentUser.getId())
                || currentUser.getAuthorities().contains(new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.toString()))) {
            findByComment.setContent(dto.getContent());
            findByComment.setDate(LocalDate.builder()
                    .updateAt(LocalDateTime.now())
                    .build());
            findByComment.setPost(findByPost);
            return commentRepository.save(findByComment);
        }

        ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "권한이 없습니다.");

        throw new UnauthorizedException(apiResponse);
    }

    @Override
    public ApiResponse deleteComment(Long postId, Long commentId, UserPrincipal currentUser) {
        postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException(POST, ID, postId));

        Comment findByComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException(COMMENT, ID, commentId));

        if (findByComment.getUserId().equals(currentUser.getId())
                || currentUser.getAuthorities().contains(new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.toString()))) {
            commentRepository.deleteById(commentId);

            return new ApiResponse(Boolean.TRUE, "게시물이 삭제 되었습니다.");
        }
        ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "권한이 없습니다.");

        throw new UnauthorizedException(apiResponse);
    }

    @Override
    public Comment isEnable(Long postId, Long commentId, UserPrincipal currentUser) {
       postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException(POST, ID, postId));

        Comment findByComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException(COMMENT, ID, commentId));

        if (findByComment.getUserId().equals(currentUser.getId())
                || currentUser.getAuthorities().contains(new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.toString()))) {

            findByComment.setIsEnable(1);

            return commentRepository.save(findByComment);
        }

        ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "권한이 없습니다.");

        throw new UnauthorizedException(apiResponse);
    }

    @Override
    public Comment isUnable(Long postId, Long commentId, UserPrincipal currentUser) {
        postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException(POST, ID, postId));

        Comment findByComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException(COMMENT, ID, commentId));

        if (findByComment.getUserId().equals(currentUser.getId())
                || currentUser.getAuthorities().contains(new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.toString()))) {

            findByComment.setIsEnable(0);

            return commentRepository.save(findByComment);
        }

        ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "권한이 없습니다.");

        throw new UnauthorizedException(apiResponse);
    }


}
