package com.spring.blog.controller;

import com.spring.blog.entity.User;
import com.spring.blog.payload.SuccessResponse;
import com.spring.blog.payload.request.JoinUserRequestDto;
import com.spring.blog.payload.response.JoinUserResponseDto;
import com.spring.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public SuccessResponse<JoinUserResponseDto> joinUser(@Valid @RequestBody JoinUserRequestDto dto) {
        User newUser = userService.joinUser(dto);

        return SuccessResponse.success(JoinUserResponseDto.builder()
                .id(newUser.getId())
                .email(newUser.getEmail())
                .name(newUser.getName())
                .createdAt(newUser.getDate().getCreatedAt())
                .build());
    }

}
