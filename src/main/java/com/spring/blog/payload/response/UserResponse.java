package com.spring.blog.payload.response;

import com.spring.blog.entity.User;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.Authentication;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Builder
@Data
public class UserResponse {

    private Long id;
    private String email;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String token;

    public static UserResponse joinUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .createdAt(user.getDate().getCreatedAt())
                .build();
    }

    public static UserResponse loginResponse(Authentication authentication, String token) {
        return UserResponse.builder()
                .email(authentication.getName())
                .token(token)
                .build();
    }

}
