package com.spring.blog.payload.response;

import com.spring.blog.entity.Category;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class CategoryResponse {

    private Long id;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

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
                .build();
    }
}
