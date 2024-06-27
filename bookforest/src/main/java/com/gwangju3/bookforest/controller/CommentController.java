package com.gwangju3.bookforest.controller;

import com.gwangju3.bookforest.domain.Comment;
import com.gwangju3.bookforest.dto.comment.CommentDTO;
import com.gwangju3.bookforest.dto.comment.CreateCommentRequest;
import com.gwangju3.bookforest.dto.comment.UpdateCommentRequest;
import com.gwangju3.bookforest.mapper.CommentMapper;
import com.gwangju3.bookforest.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("book-reviews/{bookReviewId}/comments")
    public CommentDTO createComment(
            @PathVariable("bookReviewId") Long bookReviewId,
            @RequestBody @Valid CreateCommentRequest request
    ) {
        Long commentId = commentService.createComment(bookReviewId, request);
        Comment comment = commentService.findComment(commentId);

        return CommentMapper.toDTO(comment);
    }

    @PatchMapping("book-reviews/{bookReviewId}/comments/{commentId}")
    public CommentDTO updateComment(
            @PathVariable("commentId") Long commentId,
            @RequestBody @Valid UpdateCommentRequest request
    ) {
        commentService.updateComment(commentId, request);
        Comment comment = commentService.findComment(commentId);

        return CommentMapper.toDTO(comment);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("book-reviews/{bookReviewId}/comments/{commentId}")
    public void deleteComment(
            @PathVariable("commentId") Long commentId
    ) {
        System.out.println(commentId);
        commentService.deleteComment(commentId);
    }
}
