package com.gwangju3.bookforest.service;

import com.gwangju3.bookforest.domain.BookReview;
import com.gwangju3.bookforest.domain.Comment;
import com.gwangju3.bookforest.domain.User;
import com.gwangju3.bookforest.dto.comment.CreateCommentRequest;
import com.gwangju3.bookforest.repository.BookReviewRepository;
import com.gwangju3.bookforest.repository.CommentRepository;
import com.gwangju3.bookforest.repository.UserRepository;
import com.gwangju3.bookforest.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {
    private final UserRepository userRepository;
    private final BookReviewRepository bookReviewRepository;
    private final CommentRepository commentRepository;

    public Comment findComment(Long commentId) {
        return commentRepository.findCommentById(commentId);
    }

    @Transactional
    public Long createComment(Long bookReviewId, CreateCommentRequest request) {

        User user = userRepository.findByUsername(UserUtil.extractUsername()).get(0);
        BookReview bookReview = bookReviewRepository.findBookReviewById(bookReviewId);

        Comment comment = new Comment(user, request.getContent());
        comment.setBookReview(bookReview);

        commentRepository.save(comment);
        System.out.println(comment.getContent());
        return comment.getId();
    }
}
