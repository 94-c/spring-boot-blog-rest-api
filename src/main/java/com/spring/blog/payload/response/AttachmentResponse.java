package com.spring.blog.payload.response;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Builder
@Data
public class AttachmentResponse {
    private String fileName;
    private String fileDownloadUri;
    private String fileType;
    private long size;

}
