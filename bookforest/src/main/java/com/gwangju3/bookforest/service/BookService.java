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
import com.gwangju3.bookforest.exception.book.InvalidPageException;
import com.gwangju3.bookforest.exception.global.UnauthorizedDeletionException;
import com.gwangju3.bookforest.exception.global.UnauthorizedModificationException;
import com.gwangju3.bookforest.repository.BookRepository;
import com.gwangju3.bookforest.repository.UserRepository;
import com.gwangju3.bookforest.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.InvalidPropertiesFormatException;
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

    @Transactional(readOnly = true)
    public List<MyBook> findMyBookByUserBook(Long bookId) {
        List<MyBook> myBookList = bookRepository.findMyBookByUserBook(UserUtil.extractUsername(), bookId);
        return myBookList;
    }

    @Transactional(readOnly = true)
    public List<MyBook> findReadingBookListByUserId (Long userId) {
        return bookRepository.findReadingBookListByUserId(userId);
    }

    @Transactional(readOnly = true)
    public List<MyBook> findCompletedBookListByUserId (Long userId) {
        return bookRepository.findCompletedBookListByUserId(userId);
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
        Integer readPage = page - mybook.getLastReadPage();
        boolean didUpdate = mybook.setLastReadPage(page);
        if (didUpdate) {
            commitService.createReadCommit(readPage, mybook);
            return mybook;
        } else {
            throw new InvalidPageException();
        }
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

    public QuickReview updateQuickReview(UpdateQuickReviewRequest request) {
        QuickReview quickReview = bookRepository.findQuickReviewById(request.getQuickReviewId());

        String username = UserUtil.extractUsername();
        User user = userRepository.findByUsername(username).get(0);

        if (quickReview.getUser().getId().equals(user.getId())) {
            quickReview.setContent(request.getContent());
            return quickReview;
        } else {
            throw new UnauthorizedModificationException();
        }
    }

    public void deleteQuickReview(DeleteQuickReviewRequest request) {
        QuickReview quickReview = bookRepository.findQuickReviewById(request.getQuickReviewId());

        String username = UserUtil.extractUsername();
        User user = userRepository.findByUsername(username).get(0);

        if (quickReview.getUser().getId().equals(user.getId())) {
            bookRepository.deleteQuickReview(quickReview);
        } else {
            throw new UnauthorizedDeletionException();
        }
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
