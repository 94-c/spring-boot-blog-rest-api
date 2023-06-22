package com.spring.blog.service;

import com.spring.blog.entity.Attachment;
import com.spring.blog.payload.request.AttachmentRequestDto;
import com.spring.blog.security.UserPrincipal;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface AttachmentService {

    String storeFile(MultipartFile file);

    Resource loadFileAsResource(String fileName);

    Attachment createAttachment(MultipartFile file, Long PostId, UserPrincipal currentUser);
}
