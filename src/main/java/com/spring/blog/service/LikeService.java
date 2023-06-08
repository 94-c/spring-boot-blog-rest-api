package com.spring.blog.service;

import com.spring.blog.entity.Like;
import com.spring.blog.entity.Post;
import com.spring.blog.security.UserPrincipal;

public interface LikeService {

    boolean hasLikePost(Post post, UserPrincipal currentUser);
    Like createLikePost(Post post, UserPrincipal currentUser);
    void deleteLikePost(Post post, UserPrincipal currentUser);
    Like updateLikeOfPost(Long postId, String email);

}
