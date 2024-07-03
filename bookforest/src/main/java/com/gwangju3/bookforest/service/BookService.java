package com.gwangju3.bookforest.service;

import com.gwangju3.bookforest.domain.Book;
import com.gwangju3.bookforest.domain.MyBook;
import com.gwangju3.bookforest.domain.QuickReview;
import com.gwangju3.bookforest.domain.User;
import com.gwangju3.bookforest.domain.like.BookLike;
import com.gwangju3.bookforest.dto.book.CreateBookLikeRequest;
import com.gwangju3.bookforest.dto.book.CreateQuickReviewRequest;
import com.gwangju3.bookforest.dto.book.DeleteQuickReviewRequest;
import com.gwangju3.bookforest.dto.book.UpdateQuickReviewRequest;
import com.gwangju3.bookforest.repository.BookRepository;
import com.gwangju3.bookforest.repository.UserRepository;
import com.gwangju3.bookforest.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final CommitService commitService;

    @Transactional(readOnly = true)
    public Book findBookById(Long bookId) {
        return bookRepository.findBookById(bookId);
    }

    @Transactional(readOnly = true)
    public List<Book> searchBook(String q) {
        return bookRepository.searchBook(q);
    }

    public boolean toggleBookLike(CreateBookLikeRequest request) {
        User user = userRepository.findByUsername(UserUtil.extractUsername()).get(0);
        Book book = bookRepository.findBookById(request.getBookId());

        List<BookLike> bookLikes = bookRepository.findBookLikeByUserBook(user.getId(), book.getId());

        if (bookLikes.isEmpty()) {
            BookLike bookLike = new BookLike();

            bookLike.setBook(book);
            bookLike.setUser(user);

            bookRepository.saveBookLike(bookLike);

            return true;
        } else {
            BookLike bookLike = bookLikes.get(0);
            bookRepository.deleteBookLike(bookLike);

            return false;
        }
    }
}
