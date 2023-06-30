package com.spring.blog.service.impl;

import com.spring.blog.entity.Certification;
import com.spring.blog.entity.Post;
import com.spring.blog.entity.Role;
import com.spring.blog.entity.User;
import com.spring.blog.entity.common.LocalDate;
import com.spring.blog.entity.common.RoleName;
import com.spring.blog.exception.AppException;
import com.spring.blog.exception.BadRequestException;
import com.spring.blog.exception.ResourceNotFoundException;
import com.spring.blog.exception.UnauthorizedException;
import com.spring.blog.payload.ApiResponse;
import com.spring.blog.payload.PageResponse;
import com.spring.blog.payload.request.*;
import com.spring.blog.payload.response.PostResponse;
import com.spring.blog.payload.response.UserResponse;
import com.spring.blog.repository.CertificationRepository;
import com.spring.blog.repository.RoleRepository;
import com.spring.blog.repository.UserRepository;
import com.spring.blog.security.UserPrincipal;
import com.spring.blog.service.CertificationService;
import com.spring.blog.service.UserService;
import com.spring.blog.utils.RandomNumberUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.spring.blog.utils.AppConstants.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CertificationRepository certificationRepository;
    private final CertificationService certificationService;


    @Override
    public User joinUser(JoinUserRequestDto dto) {

        if (userRepository.existsByEmail(dto.getEmail())) {
            ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "중복 된 이메일입니다.");
            throw new BadRequestException(apiResponse);
        }

        List<Role> roles = new ArrayList<>();
        roles.add(
                roleRepository.findByName(RoleName.ROLE_USER).orElseThrow(() -> new AppException("사용자 권한이 부여되지 않았습니다."))
        );

        User user = User.builder()
                .email(dto.getEmail())
                .name(dto.getName())
                .password(BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt()))
                .date(LocalDate.builder()
                        .createdAt(LocalDateTime.now())
                        .build())
                .status(0)
                .roles(roles)
                .build();

        User joinUser = userRepository.save(user);

        certificationService.createEmailToken(joinUser.getId(), joinUser.getEmail());

        return joinUser;
    }

    @Override
    public boolean longinUser(LoginRequestDto dto) {
        Optional<User> findByEmail = userRepository.findByEmail(dto.getEmail());

        if (findByEmail.isPresent()) {
            User user = findByEmail.get();
            return user.getPassword().equals(dto.getPassword()); // 로그인 성공
        }
        return false; // 로그인 실패
    }

    @Override
    public PageResponse<UserResponse> findAllUsers(int pageNo, int pageSize, String sortBy, String sortDir, String email, String name) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<User> users = userRepository.findAllSearch(email, name, pageable);

        List<User> listOfUsers = users.getContent();

        List<UserResponse> userResponses = listOfUsers.stream().map(UserResponse::convertToUserResponse).toList();

        PageResponse<UserResponse> pageResource = new PageResponse<>();

        pageResource.setContent(userResponses);
        pageResource.setPageNo(pageNo);
        pageResource.setPageSize(pageSize);
        pageResource.setTotalElements(users.getTotalElements());
        pageResource.setTotalPages(users.getTotalPages());
        pageResource.setLast(pageResource.isLast());

        return pageResource;
    }

    @Override
    public User findByUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(USER, ID, userId));
    }

    @Override
    public User updateUser(Long userId, UserRequestDto dto, UserPrincipal currentUser) {

        User findByUser = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(USER, ID, userId));

        if (findByUser.getId().equals(currentUser.getId())
                || currentUser.getAuthorities().contains(new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.toString()))) {

            findByUser.setPassword(dto.getPassword());
            findByUser.setDate(LocalDate.builder()
                    .updateAt(LocalDateTime.now())
                    .build());
            return userRepository.save(findByUser);
        }

        ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "권한이 없습니다.");

        throw new UnauthorizedException(apiResponse);
    }

    @Override
    public User isEnable(Long userId, UserPrincipal currentUser) {
        User findByUser = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(USER, ID, userId));

        if (findByUser.getId().equals(currentUser.getId())
                || currentUser.getAuthorities().contains(new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.toString()))) {
            findByUser.setStatus(1);
            findByUser.setDate(LocalDate.builder()
                    .updateAt(LocalDateTime.now())
                    .build());
            return userRepository.save(findByUser);
        }

        ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "권한이 없습니다.");

        throw new UnauthorizedException(apiResponse);
    }

    @Override
    public User isUnable(Long userId, UserPrincipal currentUser) {
        User findByUser = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(USER, ID, userId));

        if (findByUser.getId().equals(currentUser.getId())
                || currentUser.getAuthorities().contains(new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.toString()))) {
            findByUser.setStatus(0);
            findByUser.setDate(LocalDate.builder()
                    .updateAt(LocalDateTime.now())
                    .build());
            return userRepository.save(findByUser);
        }

        ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "권한이 없습니다.");

        throw new UnauthorizedException(apiResponse);
    }

    @Override
    public void findByUserPassword(FindByPasswordRequestDto dto) {
        User findByEmail = userRepository.findByEmail(dto.getEmail()).orElseThrow(() -> new RuntimeException("Not Find Email"));

        //무작위 난수로 만들기
        findByEmail.setPassword(RandomNumberUtil.getKey(6));
        findByEmail.setStatus(2);

        userRepository.save(findByEmail);

        certificationService.createPasswordToken(findByEmail.getId(), findByEmail.getEmail());
    }

    @Override
    public User findUserByPassword(String token, FindByUpdatePasswordRequestDto dto) {

        Optional<Certification> findByToken = Optional.ofNullable(certificationRepository.findById(token).orElseThrow(() -> new RuntimeException("Not Find Token")));

        User findByUser = userRepository.findById(findByToken.get().getUserId()).orElseThrow(()-> new RuntimeException("Not Found User"));

        if (findByUser != null) {
            User updateUser = User.builder()
                    .password(dto.getPassword())
                    .status(0)
                    .build();
            return userRepository.save(updateUser);
        }

        ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "권한이 없습니다.");

        throw new UnauthorizedException(apiResponse);
    }
}
