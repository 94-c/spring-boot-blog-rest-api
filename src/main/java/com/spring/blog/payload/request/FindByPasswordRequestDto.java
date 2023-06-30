package com.spring.blog.payload.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Builder
@Data
public class FindByPasswordRequestDto {
    @NotBlank(message = "이메일을 입력 해주세요.")
    private String email;

}
