package com.spring.blog.controller;

import com.spring.blog.entity.Attachment;
import com.spring.blog.entity.Post;
import com.spring.blog.payload.ApiResponse;
import com.spring.blog.payload.PageResponse;
import com.spring.blog.payload.SuccessResponse;
import com.spring.blog.payload.response.AttachmentResponse;
import com.spring.blog.payload.response.PostResponse;
import com.spring.blog.payload.request.PostRequestDto;
import com.spring.blog.security.CurrentUser;
import com.spring.blog.security.UserPrincipal;
import com.spring.blog.service.AttachmentService;
import com.spring.blog.service.PostService;
import com.spring.blog.utils.AppConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final AttachmentService attachmentService;

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<PageResponse<PostResponse>> getAllPosts(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIREACTION, required = false) String sortDir) {

        PageResponse<PostResponse> pageResponse = postService.findAllPosts(pageNo, pageSize, sortBy, sortDir);

        return new ResponseEntity<>(pageResponse, HttpStatus.OK);
    }

    @PostMapping
    //@PreAuthorize("hasRole('USER')")
    public ResponseEntity<PostResponse> createPost(@Valid @RequestBody PostRequestDto dto,
                                                   @CurrentUser UserPrincipal currentUser) {

        PostResponse createPost = postService.createPost(dto, currentUser);

        return new ResponseEntity<>(createPost, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> findByPost(@PathVariable(name = "id") Long postId) {

        Post findByPost = postService.findByPost(postId);

        return new ResponseEntity<>(findByPost, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    //@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Post> updatePost(@PathVariable(name = "id") Long postId,
                                           @Valid @RequestBody PostRequestDto dto,
                                           @CurrentUser UserPrincipal currentUser) {
        Post updatePost = postService.updatePost(postId, dto, currentUser);

        return new ResponseEntity<>(updatePost, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    //@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable(name = "id") Long postId, @CurrentUser UserPrincipal currentUser) {
        ApiResponse apiResponse = postService.deletePost(postId, currentUser);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/{id}/uploadFile")
    public ResponseEntity<Attachment> uploadFile(@PathVariable(name = "id") Long id,
                                                 @RequestParam("file") MultipartFile file,
                                                 @CurrentUser UserPrincipal currentUser) {

        Attachment createAttachment = attachmentService.createAttachment(file, id, currentUser);

        return new ResponseEntity<>(createAttachment, HttpStatus.OK);
    }

}
