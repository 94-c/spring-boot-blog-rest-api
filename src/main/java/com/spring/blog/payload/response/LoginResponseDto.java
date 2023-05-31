package com.spring.blog.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Data
public class LoginResponseDto {

    private String email;
    private String token;

}
