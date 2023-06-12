package com.spring.blog.payload.request;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AttachmentRequestDto {

    private Long id;
    private String originFileName;
    private String fullPath;
}
