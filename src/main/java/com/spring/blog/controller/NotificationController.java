package com.spring.blog.controller;

import com.spring.blog.entity.Attachment;
import com.spring.blog.entity.Notification;
import com.spring.blog.payload.ApiResponse;
import com.spring.blog.payload.PageResponse;
import com.spring.blog.payload.request.NotificationRequestDto;
import com.spring.blog.payload.response.NotificationResponse;
import com.spring.blog.security.CurrentUser;
import com.spring.blog.security.UserPrincipal;
import com.spring.blog.service.AttachmentService;
import com.spring.blog.service.NotificationService;
import com.spring.blog.utils.AppConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;
    private final AttachmentService attachmentService;

    @GetMapping
    public ResponseEntity<PageResponse<NotificationResponse>> getAllPosts(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIREACTION, required = false) String sortDir,
            @RequestParam(value = "title") String title,
            @RequestParam(value = "content") String content) {

        PageResponse<NotificationResponse> pageResponse = notificationService.findAllNotifications(pageNo, pageSize, sortBy, sortDir, title, content);

        return new ResponseEntity<>(pageResponse, HttpStatus.OK);
    }

    @PostMapping
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<NotificationResponse> createNotification(@Valid @RequestBody NotificationRequestDto dto,
                                                                   @CurrentUser UserPrincipal currentUser) {

        NotificationResponse createNotification = notificationService.createNotification(dto, currentUser);

        return new ResponseEntity<>(createNotification, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Notification> findByNotification(@PathVariable(name = "id") Long notificationId) {

        Notification findByNotification = notificationService.findByNotification(notificationId);

        return new ResponseEntity<>(findByNotification, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Notification> updateNotification(@PathVariable(name = "id") Long notificationId,
                                                           @Valid @RequestBody NotificationRequestDto dto,
                                                           @CurrentUser UserPrincipal currentUser) {

        Notification updateNotification = notificationService.updateNotification(notificationId, dto, currentUser);

        return new ResponseEntity<>(updateNotification, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> deleteNotification(@PathVariable(name = "id") Long notificationId, @CurrentUser UserPrincipal currentUser) {
        ApiResponse apiResponse = notificationService.deleteNotification(notificationId, currentUser);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }


    @PutMapping("/{id}/enable")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Notification> enableNotification(@PathVariable(name = "id") Long notificationId,
                                                           @CurrentUser UserPrincipal currentUser) {

        Notification enableNotification = notificationService.isEnable(notificationId, currentUser);

        return new ResponseEntity<>(enableNotification, HttpStatus.OK);
    }

    @PutMapping("/{id}/unable")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Notification> unableNotification(@PathVariable(name = "id") Long notificationId,
                                                           @CurrentUser UserPrincipal currentUser) {

        Notification unableNotification = notificationService.isUnable(notificationId, currentUser);

        return new ResponseEntity<>(unableNotification, HttpStatus.OK);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/{id}/uploadFile")
    public ResponseEntity<Attachment> uploadFile(@PathVariable(name = "id") Long id,
                                                 @RequestParam("file") MultipartFile file,
                                                 @CurrentUser UserPrincipal currentUser) {
        Attachment createAttachment = attachmentService.uploadAttachment(file, id, currentUser);

        return new ResponseEntity<>(createAttachment, HttpStatus.OK);
    }


    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/{id}/downloadFile/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable(name = "id") Long postId,
                                                 @PathVariable(name = "fileId") Long fileId) throws IOException {

        Attachment attachment = attachmentService.findByAttachment(fileId);

        Path path = Paths.get(attachment.getFileDownloadUri());

        Resource resource = new InputStreamResource(Files.newInputStream(path));

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + attachment.getFileName() + "\"")
                .body(resource);
    }

}
