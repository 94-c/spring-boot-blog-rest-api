package com.spring.blog.service.impl;

import com.spring.blog.entity.Role;
import com.spring.blog.entity.User;
import com.spring.blog.entity.common.LocalDate;
import com.spring.blog.entity.common.RoleName;
import com.spring.blog.exception.AppException;
import com.spring.blog.exception.BadRequestException;
import com.spring.blog.payload.ApiResponse;
import com.spring.blog.payload.request.JoinUserRequestDto;
import com.spring.blog.repository.RoleRepository;
import com.spring.blog.repository.UserRepository;
import com.spring.blog.service.CertificationService;
import com.spring.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;
    private final CertificationService certificationService;


    @Override
    public User joinUser(JoinUserRequestDto dto) {

        if (userRepository.existsByEmail(dto.getEmail())) {
            ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "중복 된 이메일입니다.");
            throw  new BadRequestException(apiResponse);
        }

        List<Role> roles = new ArrayList<>();
        roles.add(
                roleRepository.findByName(RoleName.ROLE_USER).orElseThrow(() -> new AppException("사용자 권한이 부여되지 않았습니다."))
        );

        User user = User.builder()
                .email(dto.getEmail())
                .name(dto.getName())
                .date(LocalDate.builder()
                        .createdAt(LocalDateTime.now())
                        .build())
                .status(0)
                .build();

        user.setRoles(roles);
        user.setPassword(BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt()));

        User joinUser = userRepository.save(user);

        certificationService.createEmailToken(joinUser.getId(), joinUser.getEmail());

        return joinUser;
    }

}
