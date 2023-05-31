package com.spring.blog.controller;

import com.spring.blog.entity.User;
import com.spring.blog.payload.SuccessResponse;
import com.spring.blog.payload.request.JoinUserRequestDto;
import com.spring.blog.payload.request.LoginRequestDto;
import com.spring.blog.payload.response.JoinUserResponseDto;
import com.spring.blog.payload.response.LoginResponseDto;
import com.spring.blog.security.JwtTokenProvider;
import com.spring.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

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

    @PostMapping("/login")
    public ResponseEntity<SuccessResponse<LoginResponseDto>> login(@Valid @RequestBody LoginRequestDto loginDto, HttpServletResponse response) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.createToken(authentication);

        Cookie myCookie = new Cookie("cookie", token);

        myCookie.setHttpOnly(true);
        myCookie.setMaxAge(300);
        response.addCookie(myCookie);

        System.out.println(authentication.getName());

        HttpHeaders httpHeaders = new HttpHeaders();


        return SuccessResponse.successResponseEntity(LoginResponseDto.builder()
                        .token(token)
                        .email(authentication.getName())
                        .build()
                , httpHeaders);
    }

    @GetMapping("/logout")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<SuccessResponse<String>> logout() {
        HttpHeaders httpHeaders = new HttpHeaders();

        SuccessResponse<String> successResponse = SuccessResponse.success(null);

        return new ResponseEntity<>(successResponse, httpHeaders, HttpStatus.OK);
    }


}
