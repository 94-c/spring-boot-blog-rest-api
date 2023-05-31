package com.spring.blog.payload.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Data
public class CreatePostRequestDto {

    private String title;
    private String content;
    private Long userId;
    private LocalDateTime createdAt;

}
