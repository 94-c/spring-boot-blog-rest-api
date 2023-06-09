package com.spring.blog.controller;

import com.spring.blog.entity.Notification;
import com.spring.blog.payload.ApiResponse;
import com.spring.blog.payload.PageResponse;
import com.spring.blog.payload.SuccessResponse;
import com.spring.blog.payload.request.NotificationRequestDto;
import com.spring.blog.payload.response.NotificationResponse;
import com.spring.blog.security.CurrentUser;
import com.spring.blog.security.UserPrincipal;
import com.spring.blog.service.NotificationService;
import com.spring.blog.utils.AppConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public SuccessResponse<NotificationResponse> getAllPosts(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIREACTION, required = false) String sortDir) {

        PageResponse<NotificationResponse> pageResponse = notificationService.findAllNotifications(pageNo, pageSize, sortBy, sortDir);

        return SuccessResponse.success(pageResponse);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(value = HttpStatus.CREATED)
    public SuccessResponse<NotificationResponse> createNotification(@Valid @RequestBody NotificationRequestDto dto,
                                                                    @CurrentUser UserPrincipal currentUser) {

        Notification createNotification = notificationService.createNotification(dto, currentUser);

        return SuccessResponse.success(NotificationResponse.createNotificationResponse(createNotification));
    }

    @GetMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public SuccessResponse<NotificationResponse> findByNotification(@PathVariable(name = "id") Long notificationId) {

        Notification findByNotification = notificationService.findByNotification(notificationId);

        return SuccessResponse.success(NotificationResponse.findNotificationResponse(findByNotification));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(value = HttpStatus.CREATED)
    public SuccessResponse<NotificationResponse> updateNotification(@PathVariable(name = "id") Long notificationId,
                                                                    @Valid @RequestBody NotificationRequestDto dto,
                                                                    @CurrentUser UserPrincipal currentUser) {

        Notification updateNotification = notificationService.updateNotification(notificationId, dto, currentUser);

        return SuccessResponse.success(NotificationResponse.updateNotificationResponse(updateNotification));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<ApiResponse> deleteNotification(@PathVariable(name = "id") Long notificationId, @CurrentUser UserPrincipal currentUser) {
        ApiResponse apiResponse = notificationService.deleteNotification(notificationId, currentUser);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
