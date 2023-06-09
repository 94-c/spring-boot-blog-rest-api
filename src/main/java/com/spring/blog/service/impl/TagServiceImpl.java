package com.spring.blog.service.impl;

import com.spring.blog.entity.Tag;
import com.spring.blog.entity.common.RoleName;
import com.spring.blog.exception.ResourceNotFoundException;
import com.spring.blog.exception.UnauthorizedException;
import com.spring.blog.payload.ApiResponse;
import com.spring.blog.payload.PageResponse;
import com.spring.blog.payload.request.TagRequestDto;
import com.spring.blog.payload.response.TagResponse;
import com.spring.blog.repository.TagRepository;
import com.spring.blog.security.UserPrincipal;
import com.spring.blog.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import static com.spring.blog.utils.AppConstants.ID;
import static com.spring.blog.utils.AppConstants.TAG;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    @Override
    public PageResponse<Tag> findAllTags(int pageNo, int pageSize, String sortBy, String sortDir) {
        return null;
    }

    @Override
    public TagResponse createTag(TagRequestDto dto, UserPrincipal currentUser) {

        Tag tag = Tag.builder()
                .name(dto.getName())
                .build();

        Tag newTag = tagRepository.save(tag);

        return TagResponse.createTagResponse(newTag);
    }

    @Override
    public Tag findByTag(Long tagId) {
        return tagRepository.findById(tagId).orElseThrow(() -> new ResourceNotFoundException(TAG, ID, tagId));
    }

    @Override
    public Tag updateTag(Long tagId, TagRequestDto dto, UserPrincipal currentUser) {
        Tag tag = tagRepository.findById(tagId).orElseThrow(() -> new ResourceNotFoundException("Tag", "id", tagId));
        if (tag.getDate().getCreatedAt().equals(currentUser.getId()) || currentUser.getAuthorities()
                .contains(new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.toString()))) {
            tag.setName(dto.getName());
            return tagRepository.save(tag);
        }
        ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "You don't have permission to edit this tag");

        throw new UnauthorizedException(apiResponse);
    }

    @Override
    public ApiResponse deleteTag(Long tagId, UserPrincipal currentUser) {
        Tag tag = tagRepository.findById(tagId).orElseThrow(() -> new ResourceNotFoundException("Tag", "id", tagId));
        if (tag.getDate().getCreatedAt().equals(currentUser.getId()) || currentUser.getAuthorities()
                .contains(new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.toString()))) {
            tagRepository.deleteById(tagId);
            return new ApiResponse(Boolean.TRUE, "You successfully deleted tag");
        }

        ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "You don't have permission to delete this tag");

        throw new UnauthorizedException(apiResponse);
    }
}
