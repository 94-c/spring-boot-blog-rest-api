package com.spring.blog.payload.response;

import com.spring.blog.entity.Post;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Builder
@Data
public class PostResponse {

    private Long id;
    private Long userId;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<CommentResponse> comments;
    private List<String> tags;

    public void setTags(List<String> tags) {

        if (tags == null) {
            this.tags = null;
        } else {
            this.tags = Collections.unmodifiableList(tags);
        }
    }

    public static PostResponse createPostResponse(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .userId(post.getUserId())
                .title(post.getTitle())
                .content(post.getContent())
                .createdAt(post.getDate().getCreatedAt())
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
                .comments(CommentResponse.convertToCommentDtoList(post.getComments()))
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
                .comments(CommentResponse.convertToCommentDtoList(post.getComments()))
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

    public static List<PostResponse> convertToPostResponseList(List<Post> posts) {
        Stream<Post> stream = posts.stream();

        return stream.map(PostResponse::convertToPostResponse).collect(Collectors.toList());
    }

}
