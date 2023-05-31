package com.spring.blog.service;

import com.spring.blog.entity.Category;
import com.spring.blog.exception.UnauthorizedException;
import com.spring.blog.payload.ApiResponse;
import com.spring.blog.payload.PageResponse;
import com.spring.blog.payload.request.CreateCategoryRequestDto;
import com.spring.blog.payload.request.UpdateCategoryRequestDto;
import com.spring.blog.payload.response.CategoryResponse;
import com.spring.blog.security.UserPrincipal;
import org.springframework.http.ResponseEntity;

public interface CategoryService {

    PageResponse<CategoryResponse> findByAllCategories(int pageNo, int pageSize, String sortBy, String sortDir);
    Category createCategory(CreateCategoryRequestDto dto, UserPrincipal currentUser);

    Category findByCategory(Long categoryId);

    Category updateCategory(Long categoryId, UpdateCategoryRequestDto dto, UserPrincipal currentUser) throws UnauthorizedException;

    ApiResponse deleteCategory(Long categoryId, UserPrincipal currentUser) throws UnauthorizedException;


}
