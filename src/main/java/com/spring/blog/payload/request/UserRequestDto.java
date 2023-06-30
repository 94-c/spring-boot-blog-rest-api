package com.spring.blog.payload.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Builder
@Data
public class UserRequestDto {
    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;


}
