package com.spring.blog.controller;


import com.spring.blog.entity.Tag;
import com.spring.blog.payload.ApiResponse;
import com.spring.blog.payload.request.TagRequestDto;
import com.spring.blog.payload.response.TagResponse;
import com.spring.blog.security.CurrentUser;
import com.spring.blog.security.UserPrincipal;
import com.spring.blog.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;

    @PostMapping
    //@PreAuthorize("hasRole('USER')")
    public ResponseEntity<TagResponse> addTag(@Valid @RequestBody TagRequestDto dto,
                                              @CurrentUser UserPrincipal currentUser) {
        TagResponse newTag = tagService.createTag(dto, currentUser);

        return new ResponseEntity<>(newTag, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tag> getTag(@PathVariable(name = "id") Long id) {
        Tag tag = tagService.findByTag(id);

        return new ResponseEntity<>(tag, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    //@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Tag> updateTag(@PathVariable(name = "id") Long id,
                                         @Valid @RequestBody TagRequestDto dto,
                                         @CurrentUser UserPrincipal currentUser) {

        Tag updatedTag = tagService.updateTag(id, dto, currentUser);

        return new ResponseEntity<>(updatedTag, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    //@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> deleteTag(@PathVariable(name = "id") Long id,
                                                 @CurrentUser UserPrincipal currentUser) {
        ApiResponse apiResponse = tagService.deleteTag(id, currentUser);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

}

