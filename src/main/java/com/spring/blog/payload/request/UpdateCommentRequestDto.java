package com.spring.blog.payload.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Builder
@Data
public class UpdateCommentRequestDto {

    private String content;

}
