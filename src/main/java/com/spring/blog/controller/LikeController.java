package com.spring.blog.controller;

import com.spring.blog.entity.Like;
import com.spring.blog.payload.SuccessResponse;
import com.spring.blog.payload.response.LikeResponse;
import com.spring.blog.security.UserPrincipal;
import com.spring.blog.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts/{postId}/likes")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    /*
        TODO : The given id must not be null 에러
     */
    @PostMapping()
    public ResponseEntity<Like> likePost(@PathVariable(name = "postId") Long postId,
                                         UserPrincipal currentUser) {

        Like like = likeService.updateLikeOfPost(postId, currentUser);

        return new ResponseEntity<>(like, HttpStatus.CREATED);
    }
}
