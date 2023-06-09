package com.spring.blog.service;

import com.spring.blog.entity.Tag;
import com.spring.blog.payload.ApiResponse;
import com.spring.blog.payload.PageResponse;
import com.spring.blog.payload.request.TagRequestDto;
import com.spring.blog.payload.response.TagResponse;
import com.spring.blog.security.UserPrincipal;

public interface TagService {

    PageResponse<Tag> findAllTags(int pageNo, int pageSize, String sortBy, String sortDir);

    TagResponse createTag(TagRequestDto dto, UserPrincipal currentUser);

    Tag findByTag(Long tagId);

    Tag updateTag(Long tagId, TagRequestDto dto, UserPrincipal currentUser);

    ApiResponse deleteTag(Long tagId, UserPrincipal currentUser);

}
