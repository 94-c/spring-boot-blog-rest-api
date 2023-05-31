package com.spring.blog.payload.request;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class UpdatePostRequestDto {

    private String title;
    private String content;
    private LocalDateTime updatedAt;

}
