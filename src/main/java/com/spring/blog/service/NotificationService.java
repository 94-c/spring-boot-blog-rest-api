package com.spring.blog.service;

import com.spring.blog.entity.Notification;
import com.spring.blog.payload.ApiResponse;
import com.spring.blog.payload.PageResponse;
import com.spring.blog.payload.request.NotificationRequestDto;
import com.spring.blog.payload.response.NotificationResponse;
import com.spring.blog.security.UserPrincipal;

public interface NotificationService {

    PageResponse<NotificationResponse> findAllNotifications(int pageNo, int pageSize, String sortBy, String sortDir);

    NotificationResponse createNotification(NotificationRequestDto dto, UserPrincipal currentUser);

    Notification findByNotification(Long notificationId);

    Notification updateNotification(Long notificationId, NotificationRequestDto dto, UserPrincipal currentUser);

    ApiResponse deleteNotification(Long notificationId, UserPrincipal currentUser);

}
