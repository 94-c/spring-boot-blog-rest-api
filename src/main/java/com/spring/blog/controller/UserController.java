package com.spring.blog.controller;

import com.spring.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /*
        TODO : 사용자 정보 수정
     */

    /*
        TODO : 사용자 탈퇴
     */

    /*
        TODO : 사용자 활성화
     */

    /*
        TODO : 사용자 비활성화
     */

}
