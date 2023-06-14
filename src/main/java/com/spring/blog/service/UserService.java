package com.spring.blog.service;

import com.spring.blog.entity.User;
import com.spring.blog.payload.request.JoinUserRequestDto;
import com.spring.blog.payload.request.LoginRequestDto;

public interface UserService {
    User joinUser(JoinUserRequestDto dto);
    boolean longinUser(LoginRequestDto dto);

}
