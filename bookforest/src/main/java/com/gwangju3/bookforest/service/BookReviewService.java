package com.gwangju3.bookforest.service;

import com.gwangju3.bookforest.domain.Book;
import com.gwangju3.bookforest.domain.BookReview;
import com.gwangju3.bookforest.domain.User;
import com.gwangju3.bookforest.domain.like.BookReviewLike;
import com.gwangju3.bookforest.dto.CustomUserDetails;
import com.gwangju3.bookforest.dto.UpdateBookReviewRequest;
import com.gwangju3.bookforest.dto.bookreview.CreateBookReviewRequest;
import com.gwangju3.bookforest.exception.global.UnauthorizedDeletionException;
import com.gwangju3.bookforest.exception.global.UnauthorizedModificationException;
import com.gwangju3.bookforest.repository.BookRepository;
import com.gwangju3.bookforest.repository.BookReviewRepository;
import com.gwangju3.bookforest.repository.LikeRepository;
import com.gwangju3.bookforest.repository.UserRepository;
import com.gwangju3.bookforest.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookReviewService {

    private final BookReviewRepository bookReviewRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;
    private final CommitService commitService;
    private final TierService tierService;

    public List<BookReview> searchBookReview(String q, String sortBy) {
        return bookReviewRepository.searchBookReview(q, sortBy);
    }

    public BookReview findBookReview(Long id) {
        return bookReviewRepository.findBookReviewById(id);
    }

    @Transactional
    public Long createBookReview(CreateBookReviewRequest request) {
        Long bookId = request.getBookId();
        String title = request.getTitle();
        String content = request.getContent();

        Book book = bookRepository.findBookById(bookId);

        String username = UserUtil.extractUsername();

        User writer = userRepository.findByUsername(username).get(0);

        BookReview bookReview = new BookReview(book, title, content);
        bookReview.setUser(writer);

        bookReviewRepository.save(bookReview);
        commitService.createBookReviewCommit(bookReview);

        return bookReview.getId();
    }

    @Transactional
    public void updateBookReview(UpdateBookReviewRequest request, Long bookReviewId) {
        String title = request.getTitle();
        String content = request.getContent();

        BookReview bookReview = bookReviewRepository.findBookReviewById(bookReviewId);

        String currentUsername = UserUtil.extractUsername();
        String writerUsername = bookReview.getUser().getUsername();

        if (currentUsername.equals(writerUsername)) {
            bookReview.update(title, content);
        } else {
            throw new UnauthorizedModificationException();
        }
    }

    @Transactional
    public void deleteBookReview(Long bookReviewId) {
        BookReview bookReview = bookReviewRepository.findBookReviewById(bookReviewId);

        String currentUsername = UserUtil.extractUsername();
        String writerUsername = bookReview.getUser().getUsername();

        if (currentUsername.equals(writerUsername)) {
            bookReviewRepository.delete(bookReview);
            tierService.subtractTierEXP(userRepository.findByUsername(currentUsername).get(0), 200);
        } else {
            throw new UnauthorizedDeletionException();
        }
    }

    @Transactional
    public boolean createBookReviewLike(Long bookReviewId) {
        BookReview bookReview = bookReviewRepository.findBookReviewById(bookReviewId);
        User user = userRepository.findByUsername(UserUtil.extractUsername()).get(0);

        List<BookReviewLike> bookReviewLikes = likeRepository.findBookReviewLikeByBookReviewAndUser(bookReview, user);

        if (bookReviewLikes.isEmpty()) {
            BookReviewLike bookReviewLike = new BookReviewLike();

            bookReviewLike.setUser(user);
            bookReviewLike.setBookReview(bookReview);

            likeRepository.save(bookReviewLike);

            return true;
        } else {
            BookReviewLike bookReviewLike = bookReviewLikes.get(0);
            likeRepository.delete(bookReviewLike);

            return false;
        }

    }

    public List<BookReview> findBookReviewByUserId(Long userId) {
        return bookReviewRepository.findBookReviewByUserId(userId);
    }
}
