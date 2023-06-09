package com.spring.blog.service.impl;

import com.spring.blog.entity.Category;
import com.spring.blog.entity.common.LocalDate;
import com.spring.blog.entity.common.RoleName;
import com.spring.blog.exception.ResourceNotFoundException;
import com.spring.blog.exception.UnauthorizedException;
import com.spring.blog.payload.ApiResponse;
import com.spring.blog.payload.PageResponse;
import com.spring.blog.payload.request.CategoryRequestDto;
import com.spring.blog.payload.response.CategoryResponse;
import com.spring.blog.repository.CategoryRepository;
import com.spring.blog.security.UserPrincipal;
import com.spring.blog.service.CategoryService;
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

import static com.spring.blog.utils.AppConstants.CATEGORY;
import static com.spring.blog.utils.AppConstants.ID;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional(readOnly = true)
    public PageResponse<CategoryResponse> findByAllCategories(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Category> categories = categoryRepository.findAll(pageable);

        List<Category> listOfCategories = categories.getContent();

        List<CategoryResponse> content = listOfCategories.stream().map(CategoryResponse::convertToCategoryResponse).collect(Collectors.toList());

        PageResponse<CategoryResponse> pageResponse = new PageResponse<>();

        pageResponse.setContent(content);
        pageResponse.setPageNo(pageNo);
        pageResponse.setPageSize(pageSize);
        pageResponse.setTotalElements(categories.getTotalElements());
        pageResponse.setTotalPages(categories.getTotalPages());
        pageResponse.setLast(pageResponse.isLast());

        return pageResponse;
    }

    @Override
    public CategoryResponse createCategory(CategoryRequestDto dto, UserPrincipal currentUser) {

        Category category = Category.builder()
                .name(dto.getName())
                .date(LocalDate.builder()
                        .createdAt(LocalDateTime.now())
                        .build())
                .userId(currentUser.getId())
                .build();

        Category createCategory = categoryRepository.save(category);

        return CategoryResponse.convertToCategoryResponse(category);
    }


    @Override
    public Category findByCategory(Long categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(CATEGORY, ID, categoryId));
    }


    @Override
    public Category updateCategory(Long categoryId, CategoryRequestDto dto, UserPrincipal currentUser) throws UnauthorizedException {
        Category findByCategory = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(CATEGORY, ID, categoryId));

        if (findByCategory.getUserId().equals(currentUser.getId())
                || currentUser.getAuthorities().contains(new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.toString()))) {
            findByCategory.setName(dto.getName());
            findByCategory.setDate(LocalDate.builder()
                    .updateAt(LocalDateTime.now())
                    .build());
            return categoryRepository.save(findByCategory);
        }

        ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "권한이 없습니다.");

        throw new UnauthorizedException(apiResponse);
    }

    @Override
    public ApiResponse deleteCategory(Long categoryId, UserPrincipal currentUser) throws UnauthorizedException {
        Category findByCategory = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(CATEGORY, ID, categoryId));

        if (findByCategory.getUserId().equals(currentUser.getId())
                || currentUser.getAuthorities().contains(new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.toString()))) {
            categoryRepository.deleteById(categoryId);

            return new ApiResponse(Boolean.TRUE, "카테고리가 삭제 되었습니다.");
        }

        ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "권한이 없습니다.");

        throw new UnauthorizedException(apiResponse);
    }

}
