package com.spring.blog.payload.request;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AttachmentRequestDto {
    private String fileName;
    private String fileDownloadUri;
    private String fileType;
    private Long size;

}
