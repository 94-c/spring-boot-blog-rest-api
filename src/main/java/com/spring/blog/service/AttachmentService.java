package com.spring.blog.service;

import com.spring.blog.entity.Attachment;
import com.spring.blog.payload.request.AttachmentRequestDto;
import com.spring.blog.security.UserPrincipal;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;

public interface AttachmentService {

    String storeFile(MultipartFile file);
    Resource loadFileAsResource(String fileName) throws FileNotFoundException;
    Attachment findByAttachment(Long id);
    Attachment uploadAttachment(MultipartFile file, Long PostId, UserPrincipal currentUser);

}
