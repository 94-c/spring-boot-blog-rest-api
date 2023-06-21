package com.spring.blog.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface AttachmentService {

    String storeFile(MultipartFile file);

    Resource loadFileAsResource(String fileName);

}
