package com.spring.blog.controller;

import com.spring.blog.entity.User;
import com.spring.blog.payload.PageResponse;
import com.spring.blog.payload.request.UserRequestDto;
import com.spring.blog.payload.response.PostResponse;
import com.spring.blog.payload.response.UserResponse;
import com.spring.blog.security.CurrentUser;
import com.spring.blog.security.UserPrincipal;
import com.spring.blog.service.UserService;
import com.spring.blog.utils.AppConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<PageResponse<UserResponse>> getAllUsers(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIREACTION, required = false) String sortDir,
            @RequestParam(value = "email") String email,
            @RequestParam(value = "name") String name) {

        PageResponse<UserResponse> pageResponse = userService.findAllUsers(pageNo, pageSize, sortBy, sortDir, email, name);

        return new ResponseEntity<>(pageResponse, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    //@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<User> findByUser(@PathVariable(name = "id") Long userId) {

        User findByUser = userService.findByUser(userId);

        return new ResponseEntity<>(findByUser, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    //@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<User> updateUser(@PathVariable(name = "id") Long userId,
                                           @Valid @RequestBody UserRequestDto dto,
                                           @CurrentUser UserPrincipal currentUser) {
        User updateUser = userService.updateUser(userId, dto, currentUser);

        return new ResponseEntity<>(updateUser, HttpStatus.OK);
    }

    /*
        TODO : 사용자 탈퇴
     */

    @PutMapping("/{id}/enable")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> enableUser(@PathVariable(name = "id") Long userId,
                                           @CurrentUser UserPrincipal currentUser) {
        User enableUser = userService.isEnable(userId, currentUser);

        return new ResponseEntity<>(enableUser, HttpStatus.OK);
    }

    @PutMapping("/{id}/unable")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> unableUser(@PathVariable(name = "id") Long userId,
                                           @CurrentUser UserPrincipal currentUser) {
        User unableUser = userService.isUnable(userId, currentUser);

        return new ResponseEntity<>(unableUser, HttpStatus.OK);
    }

}
