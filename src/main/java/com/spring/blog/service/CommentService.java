package com.spring.blog.service;

import com.spring.blog.entity.Comment;
import com.spring.blog.payload.ApiResponse;
import com.spring.blog.payload.PageResponse;
import com.spring.blog.payload.request.CreateCommentRequestDto;
import com.spring.blog.payload.request.UpdateCommentRequestDto;
import com.spring.blog.payload.response.CommentResponse;
import com.spring.blog.security.UserPrincipal;

public interface CommentService {

    PageResponse<CommentResponse> findAllComments(int pageNo, int pageSize, String sortBy, String sortDir);

    Comment createComment(Long postId, CreateCommentRequestDto dto, UserPrincipal currentUser);

    Comment findByComment(Long postId, Long commentId);

    Comment updateComment(Long postId, Long commentId, UpdateCommentRequestDto dto, UserPrincipal currentUser);

    ApiResponse deleteComment(Long postId, Long commentId, UserPrincipal currentUser);

    Comment isEnable(Long postId, Long commentId, UserPrincipal currentUser);

    Comment isUnable(Long postId, Long commentId, UserPrincipal currentUser);

}
