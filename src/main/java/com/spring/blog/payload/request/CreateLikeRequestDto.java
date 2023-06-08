package com.spring.blog.payload.request;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class CreateLikeRequestDto {

    private Long userId;
    private boolean status;
    private LocalDateTime createdAt;

}
