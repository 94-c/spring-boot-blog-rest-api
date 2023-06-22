package com.spring.blog.service;

import com.spring.blog.entity.Category;
import com.spring.blog.exception.UnauthorizedException;
import com.spring.blog.payload.ApiResponse;
import com.spring.blog.payload.PageResponse;
import com.spring.blog.payload.request.CategoryRequestDto;
import com.spring.blog.payload.response.CategoryResponse;
import com.spring.blog.security.UserPrincipal;

public interface CategoryService {

    PageResponse<CategoryResponse> findByAllCategories(int pageNo, int pageSize, String sortBy, String sortDir);
    CategoryResponse createCategory(CategoryRequestDto dto, UserPrincipal currentUser);
    Category findByCategory(Long categoryId);

    Category updateCategory(Long categoryId, CategoryRequestDto dto, UserPrincipal currentUser) throws UnauthorizedException;

    ApiResponse deleteCategory(Long categoryId, UserPrincipal currentUser) throws UnauthorizedException;


}
