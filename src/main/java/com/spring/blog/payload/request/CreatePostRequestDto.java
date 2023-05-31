package com.spring.blog.payload.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Builder
@Data
public class CreatePostRequestDto {

    @NotBlank(message = "제목을 입력해주세요.")
    private String title;
    @NotBlank(message = "본문을 입력해주세요.")
    private String content;
    private Long userId;
    private LocalDateTime createdAt;
    @NotNull
    private Long categoryId;

}
