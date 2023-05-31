package com.spring.blog.service.impl;

import com.spring.blog.entity.Notification;
import com.spring.blog.entity.User;
import com.spring.blog.entity.common.LocalDate;
import com.spring.blog.entity.common.RoleName;
import com.spring.blog.exception.ResourceNotFoundException;
import com.spring.blog.exception.UnauthorizedException;
import com.spring.blog.payload.ApiResponse;
import com.spring.blog.payload.PageResponse;
import com.spring.blog.payload.request.CreateNotificationRequestDto;
import com.spring.blog.payload.request.UpdateNotificationRequestDto;
import com.spring.blog.payload.response.NotificationResponse;
import com.spring.blog.repository.NotificationRepository;
import com.spring.blog.repository.UserRepository;
import com.spring.blog.security.UserPrincipal;
import com.spring.blog.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.spring.blog.utils.AppConstants.*;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;


    @Override
    @Transactional(readOnly = true)
    public PageResponse<NotificationResponse> findAllNotifications(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Notification> posts = notificationRepository.findAll(pageable);

        List<Notification> listOfPosts = posts.getContent();

        List<NotificationResponse> content = listOfPosts.stream().map(NotificationResponse::convertToNotificationDto).collect(Collectors.toList());

        PageResponse<NotificationResponse> pageResource = new PageResponse<>();

        pageResource.setContent(content);
        pageResource.setPageNo(pageNo);
        pageResource.setPageSize(pageSize);
        pageResource.setTotalElements(posts.getTotalElements());
        pageResource.setTotalPages(posts.getTotalPages());
        pageResource.setLast(pageResource.isLast());

        return pageResource;
    }

    @Override
    public Notification createNotification(CreateNotificationRequestDto dto, UserPrincipal currentUser) {
        User user = userRepository.findById(currentUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException(USER, ID, 1L));

        Notification notification = Notification.builder()
                .user(user)
                .title(dto.getTitle())
                .content(dto.getContent())
                .date(LocalDate.builder()
                        .createdAt(LocalDateTime.now())
                        .build())
                .build();

        return notificationRepository.save(notification);
    }

    @Override
    public Notification findByNotification(Long notificationId) {
        return notificationRepository.findById(notificationId).orElseThrow(() -> new ResourceNotFoundException(NOTIFICATION, ID, notificationId));
    }

    @Override
    public Notification updateNotification(Long notificationId, UpdateNotificationRequestDto dto, UserPrincipal currentUser) {
        User user = userRepository.findById(currentUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException(USER, ID, 1L));

        Notification findByNotification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new ResourceNotFoundException(NOTIFICATION, ID, notificationId));

        if (findByNotification.getUser().equals(user)
                || currentUser.getAuthorities().contains(new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.toString()))) {
            findByNotification.setTitle(dto.getTitle());
            findByNotification.setContent(dto.getContent());
            findByNotification.setDate(LocalDate.builder()
                    .updateAt(LocalDateTime.now())
                    .build());
            return notificationRepository.save(findByNotification);
        }

        ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "권한이 없습니다.");

        throw new UnauthorizedException(apiResponse);
    }

    @Override
    public ApiResponse deleteNotification(Long notificationId, UserPrincipal currentUser) {

        User user = userRepository.findById(currentUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException(USER, ID, 1L));

        Notification findByNotification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new ResourceNotFoundException(NOTIFICATION, ID, notificationId));

        if (findByNotification.getUser().equals(user)
                || currentUser.getAuthorities().contains(new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.toString()))) {
            notificationRepository.deleteById(notificationId);

            return new ApiResponse(Boolean.TRUE, "공지사항이 삭제 되었습니다.");
        }

        ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "권한이 없습니다.");

        throw new UnauthorizedException(apiResponse);
    }
}
