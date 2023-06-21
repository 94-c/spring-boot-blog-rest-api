package com.spring.blog.payload.response;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Builder
@Data
public class AttachmentResponse {

    private String originFileName;
    private String fullPath;
    private Long fileSize;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
