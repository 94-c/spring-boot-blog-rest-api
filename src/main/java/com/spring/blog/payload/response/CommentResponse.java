package com.spring.blog.payload.response;

import com.spring.blog.entity.Comment;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Builder
@Data
public class CommentResponse {

    private Long id;
    private String content;
    private Long userId;
    private Long parentId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long isEnable;

    public static CommentResponse createCommentResponse(Comment comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .userId(comment.getUserId())
                .content(comment.getContent())
                .createdAt(comment.getDate().getCreatedAt())
                .build();
    }

    public static CommentResponse findCommentResponse(Comment comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .userId(comment.getUserId())
                .content(comment.getContent())
                .createdAt(comment.getDate().getCreatedAt())
                .updatedAt(comment.getDate().getUpdateAt())
                .build();
    }

    public static CommentResponse updateCommentResponse(Comment comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .userId(comment.getUserId())
                .content(comment.getContent())
                .updatedAt(comment.getDate().getUpdateAt())
                .build();
    }
    public static CommentResponse isEnableCommentResponse(Comment comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .userId(comment.getUserId())
                .content(comment.getContent())
                .createdAt(comment.getDate().getCreatedAt())
                .updatedAt(comment.getDate().getUpdateAt())
                .build();
    }

    public static CommentResponse convertToCommentResponse(Comment comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .userId(comment.getUserId())
                .content(comment.getContent())
                .createdAt(comment.getDate().getCreatedAt())
                .updatedAt(comment.getDate().getUpdateAt())
                .build();
    }



    public static List<CommentResponse> convertToCommentDtoList(List<Comment> comments) {
        Stream<Comment> stream = comments.stream();

        return stream.map(CommentResponse::convertToCommentResponse).collect(Collectors.toList());
    }


}
