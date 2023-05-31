package com.spring.blog.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SuccessResponse<T> {

    private String message;
    private T data;

    public static <T> SuccessResponse success(T data) {

        return SuccessResponse.builder()
                .message("success")
                .data(data)
                .build();
    }

    public static <T> SuccessResponse success(List<T> data) {

        return SuccessResponse.builder()
                .message("success")
                .data(data)
                .build();
    }

    public static <T> ResponseEntity<SuccessResponse<T>> successResponseEntity(T data, HttpHeaders httpHeaders) {
        SuccessResponse<T> successResponse = SuccessResponse.<T>builder()
                .message("success")
                .data(data)
                .build();

        httpHeaders.add("Content-Type", "application/json");

        return new ResponseEntity<>(successResponse, httpHeaders, HttpStatus.OK);
    }

}
