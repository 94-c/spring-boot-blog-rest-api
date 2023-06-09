package com.spring.blog.payload.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Builder
@Data
public class CommentRequestDto {

    @NotBlank(message = "댓글을 작성해주세요.")
    private String content;
    private Long userId;
    private LocalDateTime createdAt;

}
