package com.gwangju3.bookforest.service;

import com.gwangju3.bookforest.domain.BookReview;
import com.gwangju3.bookforest.domain.Comment;
import com.gwangju3.bookforest.domain.User;
import com.gwangju3.bookforest.domain.commit.Commit;
import com.gwangju3.bookforest.dto.comment.CreateCommentRequest;
import com.gwangju3.bookforest.dto.comment.UpdateCommentRequest;
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
    private final CommitService commitService;
    private final TierService tierService;

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
        commitService.createCommentCommit(comment);
        return comment.getId();
    }

    @Transactional
    public void updateComment(Long commentId, UpdateCommentRequest request) {
        Comment comment = commentRepository.findCommentById(commentId);

        String currentUsername = UserUtil.extractUsername();
        String writerUsername = comment.getUser().getUsername();

        if (currentUsername.equals(writerUsername)) {
            comment.updateContent(request.getContent());
        }
    }

    @Transactional
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findCommentById(commentId);

        String currentUsername = UserUtil.extractUsername();
        String writerUsername = comment.getUser().getUsername();

        if (currentUsername.equals(writerUsername)) {
            commentRepository.delete(comment);
            tierService.subtractTierEXP(userRepository.findByUsername(currentUsername).get(0), 10);
        }
    }
}
