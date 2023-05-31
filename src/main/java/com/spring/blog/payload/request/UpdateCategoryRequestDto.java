package com.spring.blog.payload.request;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class UpdateCategoryRequestDto {

    private String name;

    private LocalDateTime updatedAt;

}
