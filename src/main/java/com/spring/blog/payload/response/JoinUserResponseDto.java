package com.spring.blog.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.spring.blog.payload.dto.RoleDto;
import lombok.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JoinUserResponseDto {

    private Long id;
    private String email;
    private String name;
    private LocalDateTime createdAt;
    private List<RoleDto> role;

}
