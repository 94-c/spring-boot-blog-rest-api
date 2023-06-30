package com.spring.blog.service;

import com.spring.blog.entity.User;
import com.spring.blog.payload.PageResponse;
import com.spring.blog.payload.request.*;
import com.spring.blog.payload.response.UserResponse;
import com.spring.blog.security.UserPrincipal;

public interface UserService {
    User joinUser(JoinUserRequestDto dto);
    boolean longinUser(LoginRequestDto dto);
    PageResponse<UserResponse> findAllUsers(int pageNo, int pageSize, String sortBy, String sortDir, String email, String name);
    User findByUser(Long userId);
    User updateUser(Long userId, UserRequestDto dto, UserPrincipal currentUser);
    User isEnable(Long userId, UserPrincipal currentUser);
    User isUnable(Long userId, UserPrincipal currentUser);
    void findByUserPassword(FindByPasswordRequestDto dto);
    User findUserByPassword(String token, FindByUpdatePasswordRequestDto dto);

}
