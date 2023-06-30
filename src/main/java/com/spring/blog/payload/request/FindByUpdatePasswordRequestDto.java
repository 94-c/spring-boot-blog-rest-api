package com.spring.blog.payload.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Builder
@Data
public class FindByUpdatePasswordRequestDto {
    @NotBlank(message = "비밀번호를 입력 해주세요.")
    private String password;

}
