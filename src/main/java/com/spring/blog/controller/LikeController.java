package com.spring.blog.controller;

import com.spring.blog.entity.Like;
import com.spring.blog.payload.SuccessResponse;
import com.spring.blog.payload.response.LikeResponse;
import com.spring.blog.security.UserPrincipal;
import com.spring.blog.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts/{postId}/likes")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping()
    @ResponseStatus(value = HttpStatus.CREATED)
    public SuccessResponse<LikeResponse> likePost(@PathVariable(name = "id") Long postId,
                                                  UserPrincipal currentUser) {
        Like like = likeService.updateLikeOfPost(postId, currentUser);

        return SuccessResponse.success(LikeResponse.createLikeResponse(like));
    }
}
