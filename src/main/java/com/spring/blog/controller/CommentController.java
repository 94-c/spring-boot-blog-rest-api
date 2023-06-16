package com.spring.blog.controller;

import com.spring.blog.entity.Comment;
import com.spring.blog.payload.ApiResponse;
import com.spring.blog.payload.PageResponse;
import com.spring.blog.payload.SuccessResponse;
import com.spring.blog.payload.request.CommentRequestDto;
import com.spring.blog.payload.response.CommentResponse;
import com.spring.blog.security.CurrentUser;
import com.spring.blog.security.UserPrincipal;
import com.spring.blog.service.CommentService;
import com.spring.blog.utils.AppConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/posts/{postId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    /**
     * 댓글 전체 조회
     */
    @GetMapping
    public ResponseEntity<PageResponse<CommentResponse>> getAllPosts(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIREACTION, required = false) String sortDir) {

        PageResponse<CommentResponse> pageResponse = commentService.findAllComments(pageNo, pageSize, sortBy, sortDir);

        return new ResponseEntity<>(pageResponse, HttpStatus.OK);
    }

    /**
     * 댓글 등록
     */
    @PostMapping
    //@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<CommentResponse> createComment(@PathVariable(name = "postId") Long postId,
                                                          @Valid @RequestBody CommentRequestDto dto,
                                                          @CurrentUser UserPrincipal currentUser) {

        CommentResponse createComment = commentService.createComment(postId, dto, currentUser);

        return new ResponseEntity<>(createComment, HttpStatus.CREATED);
    }

    /**
     * 댓글 상세보기
     */
    @GetMapping("/{id}")
    public ResponseEntity<Comment> findByComment(@PathVariable(name = "postId") Long postId,
                                                          @PathVariable(name = "id") Long id) {
        Comment findByComment =commentService.findByComment(postId, id);

        return new ResponseEntity<>(findByComment, HttpStatus.OK);
    }

    /**
     * 댓글 수정
     */
    @PutMapping("/{id}")
    //@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Comment> updateComment(@PathVariable(name = "postId") Long postId,
                                                          @PathVariable(name = "id") Long id,
                                                          @Valid @RequestBody CommentRequestDto dto,
                                                          @CurrentUser UserPrincipal currentUser) {

        Comment updateComment = commentService.updateComment(postId, id, dto, currentUser);

        return new ResponseEntity<>(updateComment, HttpStatus.OK);
    }

    /**
     * 댓글 삭제
     */
    @DeleteMapping("/{id}")
    //@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable(name = "postId") Long postId,
                                                     @PathVariable(name = "id") Long id,
                                                     @CurrentUser UserPrincipal currentUser) {
        ApiResponse apiResponse = commentService.deleteComment(postId, id, currentUser);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}/enable")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Comment> enableComment(@PathVariable(name = "postId") Long postId,
                                                          @PathVariable(name = "id") Long id,
                                                          @CurrentUser UserPrincipal currentUser) {

        Comment isEnableComment = commentService.isEnable(postId, id, currentUser);

        return new ResponseEntity<>(isEnableComment, HttpStatus.OK);
    }

    @PutMapping("/{id}/unable")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Comment> unableComment(@PathVariable(name = "postId") Long postId,
                                                          @PathVariable(name = "id") Long id,
                                                          @CurrentUser UserPrincipal currentUser) {

        Comment isUnableComment = commentService.isUnable(postId, id, currentUser);

        return new ResponseEntity<>(isUnableComment, HttpStatus.OK);
    }

}
