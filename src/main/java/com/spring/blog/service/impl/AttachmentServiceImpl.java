package com.spring.blog.service.impl;

import com.spring.blog.entity.Attachment;
import com.spring.blog.entity.Post;
import com.spring.blog.entity.common.LocalDate;
import com.spring.blog.exception.FileStorageException;
import com.spring.blog.exception.MyFileNotFoundException;
import com.spring.blog.payload.request.AttachmentRequestDto;
import com.spring.blog.repository.AttachmentRepository;
import com.spring.blog.repository.PostRepository;
import com.spring.blog.service.AttachmentService;
import com.spring.blog.utils.AttachmentUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttachmentServiceImpl implements AttachmentService {

    private final Path fileStorageLocation;
    private final AttachmentRepository attachmentRepository;
    private final PostRepository postRepository;

    @Autowired
    public AttachmentServiceImpl(AttachmentUtil attachmentUtil, AttachmentRepository attachmentRepository, PostRepository postRepository) {
        String uploadDir = attachmentUtil.getUploadDir();
        if (uploadDir == null) {
            throw new IllegalArgumentException("Upload directory path is null. Check AttachmentUtil configuration.");
        }

        this.fileStorageLocation = Paths.get(attachmentUtil.getUploadDir()).toAbsolutePath().normalize();
        this.attachmentRepository = attachmentRepository;
        this.postRepository = postRepository;

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String storeFile(MultipartFile file) {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        try {
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    @Override
    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found" + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("File not found " + fileName, ex);
        }
    }

    @Override
    public Attachment createAttachment(MultipartFile file, Long postId) {
        String fileName = this.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/post")
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();

        Optional<Post> findByPost = postRepository.findById(postId);

        Attachment attachment = Attachment.builder()
                .fileName(fileName)
                .fileDownloadUri(fileDownloadUri)
                .fileType(file.getContentType())
                .size(file.getSize())
                .postId(findByPost.get().getId())
                .date(LocalDate.builder()
                        .createdAt(LocalDateTime.now())
                        .build())
                .build();

        return attachmentRepository.save(attachment);
    }


}
