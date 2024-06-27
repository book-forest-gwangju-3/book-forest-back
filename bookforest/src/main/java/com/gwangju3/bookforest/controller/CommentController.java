package com.gwangju3.bookforest.controller;

import com.gwangju3.bookforest.domain.Comment;
import com.gwangju3.bookforest.dto.comment.CommentDTO;
import com.gwangju3.bookforest.dto.comment.CreateCommentRequest;
import com.gwangju3.bookforest.mapper.CommentMapper;
import com.gwangju3.bookforest.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("book-reviews/{bookReviewId}/comments")
    public CommentDTO createComment(
            @PathVariable("bookReviewId") Long bookReviewId,
            @RequestBody @Valid CreateCommentRequest request) {
        
        Long commentId = commentService.createComment(bookReviewId, request);
        Comment comment = commentService.findComment(commentId);

        return CommentMapper.toDTO(comment);
    }
}
