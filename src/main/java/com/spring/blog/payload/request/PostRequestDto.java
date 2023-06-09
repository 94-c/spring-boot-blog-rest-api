package com.spring.blog.payload.request;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Builder
@Data
public class PostRequestDto {

    @NotBlank(message = "제목을 입력해주세요.")
    private String title;
    @NotBlank(message = "본문을 입력해주세요.")
    private String content;
    private Long userId;
    @NotNull
    private Long categoryId;
    private List<String> tags;

    public List<String> getTags() {
        return tags == null ? Collections.emptyList() : new ArrayList<>(tags);
    }

}
