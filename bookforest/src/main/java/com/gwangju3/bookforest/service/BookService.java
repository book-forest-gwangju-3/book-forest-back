package com.gwangju3.bookforest.service;

import com.gwangju3.bookforest.domain.Book;
import com.gwangju3.bookforest.domain.MyBook;
import com.gwangju3.bookforest.domain.QuickReview;
import com.gwangju3.bookforest.domain.User;
import com.gwangju3.bookforest.dto.CustomUserDetails;
import com.gwangju3.bookforest.dto.book.CreateQuickReviewRequest;
import com.gwangju3.bookforest.repository.BookRepository;
import com.gwangju3.bookforest.repository.UserRepository;
import com.gwangju3.bookforest.util.UserUtil;
import jdk.jfr.Frequency;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<Book> findAllBooks() {
        return bookRepository.findAllBook();
    }

    public MyBook createMyBook(Long bookId) {
        String username = UserUtil.extractUsername();
        User user = userRepository.findByUsername(username).get(0);

        Book book = bookRepository.findBookById(bookId);
        MyBook myBook = new MyBook(0, false);
        myBook.setBook(book);

        myBook.setUser(user);
        myBook.setBook(book);

        bookRepository.saveMyBook(myBook);
        return myBook;
    }


    public MyBook updateMyBook(long bookId, Integer page) {
        MyBook mybook = bookRepository.findMyBookByUserBook(UserUtil.extractUsername(), bookId).get(0);
        boolean didUpdate = mybook.setLastReadPage(page);
        return (didUpdate) ? mybook : null;
    }

    public QuickReview createQuickReview(CreateQuickReviewRequest request) {
        String username = UserUtil.extractUsername();
        User user = userRepository.findByUsername(username).get(0);

        Book book = bookRepository.findBookById(request.getBookId());

        QuickReview quickReview = new QuickReview(user, request.getContent());
        quickReview.setBook(book);

        bookRepository.saveQuickReview(quickReview);
        return quickReview;
    }

//    public QuickReview updateQuickReview(CreateQuickReviewRequest request) {
//
//    }
}
