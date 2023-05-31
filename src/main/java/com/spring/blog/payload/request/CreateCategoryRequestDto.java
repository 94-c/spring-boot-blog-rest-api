package com.spring.blog.payload.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Builder
@Data
public class CreateCategoryRequestDto {

    @NotBlank(message = "카테고리명을 작성해주세요.")
    private String name;

    private LocalDateTime createdAt;

}
