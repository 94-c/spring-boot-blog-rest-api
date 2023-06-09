package com.spring.blog.service;

import com.spring.blog.entity.Like;
import com.spring.blog.entity.Post;
import com.spring.blog.security.UserPrincipal;

public interface LikeService {

    boolean hasLikePost(Post post, Long userId);
    Like createLikePost(Post post, Long userId);
    void deleteLikePost(Post post, Long userId);
    Like updateLikeOfPost(Long postId, UserPrincipal currentUser);

}
