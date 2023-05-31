package com.spring.blog.payload.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginRequestDto {

    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;

    @NotBlank(message = "패스워드를 입력해주세요.")
    private String password;
}
