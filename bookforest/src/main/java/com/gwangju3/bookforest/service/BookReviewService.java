package com.gwangju3.bookforest.service;

import com.gwangju3.bookforest.domain.Book;
import com.gwangju3.bookforest.domain.BookReview;
import com.gwangju3.bookforest.domain.User;
import com.gwangju3.bookforest.dto.CustomUserDetails;
import com.gwangju3.bookforest.dto.bookreview.CreateBookReviewRequest;
import com.gwangju3.bookforest.repository.BookRepository;
import com.gwangju3.bookforest.repository.BookReviewRepository;
import com.gwangju3.bookforest.repository.UserRepository;
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

    public List<BookReview> findAll() {
        return bookReviewRepository.findAll();
    }

    public BookReview findBook(Long id) {
        return bookReviewRepository.findBookReviewById(id);
    }

    @Transactional
    public Long createBookReview(CreateBookReviewRequest request) {
        Long bookId = request.getBookId();
        String title = request.getTitle();
        String content = request.getContent();

        Book book = bookRepository.findBookById(bookId);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        User writer = userRepository.findByUsername(username).get(0);

        BookReview bookReview = new BookReview(book, title, content);
        bookReview.setUser(writer);

        bookReviewRepository.save(bookReview);

        return bookReview.getId();
    }
}
