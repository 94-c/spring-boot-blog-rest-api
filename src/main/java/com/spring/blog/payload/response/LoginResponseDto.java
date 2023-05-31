package com.spring.blog.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginResponseDto {

    private Long id;
    private String email;
    private String name;
    private LocalDateTime createdAt;
    private String token;

}
