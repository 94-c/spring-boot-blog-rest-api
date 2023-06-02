package com.spring.blog.payload.response;

import com.spring.blog.entity.Notification;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@Builder
public class NotificationResponse {

    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UserResponse user;

    public static NotificationResponse createNotificationResponse(Notification notification) {
        return NotificationResponse.builder()
                .id(notification.getId())
                .title(notification.getTitle())
                .content(notification.getContent())
                .createdAt(notification.getDate().getCreatedAt())
                .user(UserResponse.convertToUserResponse(notification.getUser()))
                .build();
    }

    public static NotificationResponse findNotificationResponse(Notification notification) {
        return NotificationResponse.builder()
                .id(notification.getId())
                .title(notification.getTitle())
                .content(notification.getContent())
                .createdAt(notification.getDate().getCreatedAt())
                .updatedAt(notification.getDate().getUpdateAt())
                .user(UserResponse.convertToUserResponse(notification.getUser()))
                .build();
    }

    public static NotificationResponse updateNotificationResponse(Notification notification) {
        return NotificationResponse.builder()
                .id(notification.getId())
                .title(notification.getTitle())
                .content(notification.getContent())
                .updatedAt(notification.getDate().getUpdateAt())
                .user(UserResponse.convertToUserResponse(notification.getUser()))
                .build();
    }

    public static NotificationResponse convertToNotificationDto(Notification notification) {
        return NotificationResponse.builder()
                .id(notification.getId())
                .title(notification.getTitle())
                .content(notification.getContent())
                .createdAt(notification.getDate().getCreatedAt())
                .updatedAt(notification.getDate().getUpdateAt())
                .user(UserResponse.convertToUserResponse(notification.getUser()))
                .build();
    }

}
