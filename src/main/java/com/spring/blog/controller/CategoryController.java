package com.spring.blog.controller;

import com.spring.blog.entity.Category;
import com.spring.blog.exception.UnauthorizedException;
import com.spring.blog.payload.ApiResponse;
import com.spring.blog.payload.PageResponse;
import com.spring.blog.payload.SuccessResponse;
import com.spring.blog.payload.request.CreateCategoryRequestDto;
import com.spring.blog.payload.request.UpdateCategoryRequestDto;
import com.spring.blog.payload.response.CategoryResponse;
import com.spring.blog.payload.response.PostResponse;
import com.spring.blog.security.CurrentUser;
import com.spring.blog.security.UserPrincipal;
import com.spring.blog.service.CategoryService;
import com.spring.blog.utils.AppConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public SuccessResponse<CategoryResponse> getAllCategories(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIREACTION, required = false) String sortDir) {

        PageResponse<CategoryResponse> pageResponse = categoryService.findByAllCategories(pageNo, pageSize, sortBy, sortDir);

        return SuccessResponse.success(pageResponse);
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(value = HttpStatus.CREATED)
    public SuccessResponse<CategoryResponse> createCategories(@Valid @RequestBody CreateCategoryRequestDto dto,
                                                              @CurrentUser UserPrincipal currentUser) {
        Category createCategory = categoryService.createCategory(dto, currentUser);

        return SuccessResponse.success(CategoryResponse.createCategoryResponse(createCategory));
    }

    @GetMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public SuccessResponse<CategoryResponse> getCategory(@PathVariable(name = "id") Long id) {
        Category findByCategory = categoryService.findByCategory(id);

        return SuccessResponse.success(CategoryResponse.convertToCategoryResponse(findByCategory));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @ResponseStatus(value = HttpStatus.OK)
    public SuccessResponse<CategoryResponse> updateCategory(@PathVariable(name = "id") Long id,
                                                            @Valid @RequestBody UpdateCategoryRequestDto dto,
                                                            @CurrentUser UserPrincipal currentUser) throws UnauthorizedException {
        Category updateCategory = categoryService.updateCategory(id, dto, currentUser);

        return SuccessResponse.success(CategoryResponse.updateCategoryResponse(updateCategory));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable(name = "id") Long id,
                                                       @CurrentUser UserPrincipal currentUser) throws UnauthorizedException {
        ApiResponse apiResponse = categoryService.deleteCategory(id, currentUser);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
