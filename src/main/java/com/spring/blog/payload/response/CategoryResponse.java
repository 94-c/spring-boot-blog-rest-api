package com.spring.blog.payload.response;

import com.spring.blog.entity.Category;
import com.spring.blog.entity.Post;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
public class CategoryResponse {

    private Long id;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<PostResponse> posts;

    public static CategoryResponse createCategoryResponse(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .createdAt(category.getDate().getCreatedAt())
                .build();
    }

    public static CategoryResponse updateCategoryResponse(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .updatedAt(category.getDate().getUpdateAt())
                .build();
    }

    public static CategoryResponse convertToCategoryResponse(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .createdAt(category.getDate().getCreatedAt())
                .updatedAt(category.getDate().getUpdateAt())
                .posts(PostResponse.convertToPostResponseList(category.getPosts()))
                .build();
    }
}
