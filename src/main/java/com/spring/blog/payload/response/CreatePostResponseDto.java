package com.spring.blog.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Data
public class CreatePostResponseDto {
    private Long id;
    private String title;
    private String content;
    private Long userId;
    private LocalDateTime createdAt;

}
