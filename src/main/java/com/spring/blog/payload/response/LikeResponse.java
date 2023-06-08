package com.spring.blog.payload.response;

import com.spring.blog.entity.Like;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LikeResponse {

    private Long id;
    private Long userId;
    private boolean status;
    private PostResponse posts;

    public static LikeResponse createLikeResponse(Like like) {
        return LikeResponse.builder()
                .id(like.getId())
                .userId(like.getUserId())
                .status(like.isStatus())
                .posts(PostResponse.convertToPostResponse(like.getPost()))
                .build();
    }

}
