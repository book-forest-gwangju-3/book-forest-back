package com.gwangju3.bookforest.service;

import com.gwangju3.bookforest.domain.Book;
import com.gwangju3.bookforest.domain.MyBook;
import com.gwangju3.bookforest.domain.User;
import com.gwangju3.bookforest.dto.CustomUserDetails;
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
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public List<Book> findAllBooks() {
        return bookRepository.findAllBook();
    }

    @Transactional
    public MyBook createMyBook(Long bookId) {
        String username = UserUtil.extractUsername();
        List<User> user = userRepository.findByUsername(username);

        Book book = bookRepository.findBookById(bookId);
        MyBook myBook = new MyBook(0, false);
        myBook.setBook(book);

        myBook.setUser(user.get(0));
        myBook.setBook(book);

        bookRepository.saveMyBook(myBook);
        return myBook;
    }


    @Transactional
    public MyBook updateMyBook(long bookId, Integer page) {
        MyBook mybook = bookRepository.findMyBookByUserBook(UserUtil.extractUsername(), bookId).get(0);
        mybook.setLastReadPage(page);
        return mybook;
    }


}
