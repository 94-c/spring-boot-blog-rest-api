package com.spring.blog.payload.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@Builder
public class UpdateNotificationRequestDto {

    @NotBlank(message = "공지사항 제목을 입력해주세요.")
    private String title;
    @NotBlank(message = "본문을 작성해주세요.")
    private String content;
    private LocalDateTime updatedAt;

}
