package com.spring.blog.service;

import com.spring.blog.entity.User;
import com.spring.blog.payload.request.JoinUserRequestDto;

public interface UserService {
    User joinUser(JoinUserRequestDto dto);

}
