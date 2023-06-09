package com.spring.blog.payload.response;

import com.spring.blog.entity.Tag;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TagResponse {

    private Long id;
    private String name;

    public static TagResponse createTagResponse(Tag tag) {
        return TagResponse.builder()
                .id(tag.getId())
                .name(tag.getName())
                .build();
    }

}
