package com.spring.blog.payload.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JoinUserRequestDto {

    private String email;

    private String password;

    private String name;


}
