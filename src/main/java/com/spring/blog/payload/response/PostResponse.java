package com.spring.blog.payload.response;

import com.spring.blog.entity.Post;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
public class PostResponse {

    private Long id;
    private Long userId;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static PostResponse createPostResponse(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .userId(post.getUserId())
                .title(post.getTitle())
                .content(post.getContent())
                .createdAt(post.getDate().getCreatedAt())
                .build();
    }

    public static PostResponse updatePostResponse(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .userId(post.getUserId())
                .title(post.getTitle())
                .content(post.getContent())
                .createdAt(post.getDate().getCreatedAt())
                .updatedAt(post.getDate().getUpdateAt())
                .build();
    }

    public static PostResponse findByPostResponse(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .userId(post.getUserId())
                .title(post.getTitle())
                .content(post.getContent())
                .createdAt(post.getDate().getCreatedAt())
                .updatedAt(post.getDate().getUpdateAt())
                .build();
    }

    public static PostResponse convertToPostResponse(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .userId(post.getUserId())
                .title(post.getTitle())
                .content(post.getContent())
                .createdAt(post.getDate().getCreatedAt())
                .updatedAt(post.getDate().getUpdateAt())
                .build();
    }

}
