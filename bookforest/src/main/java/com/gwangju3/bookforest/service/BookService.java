package com.gwangju3.bookforest.service;

import com.gwangju3.bookforest.domain.Book;
import com.gwangju3.bookforest.domain.MyBook;
import com.gwangju3.bookforest.dto.CustomUserDetails;
import com.gwangju3.bookforest.repository.BookRepository;
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
public class BookService {
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public List<Book> findAllBooks() {
        return bookRepository.findAllBook();
    }

    public MyBook createMyBook(Long bookId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        Book book = bookRepository.findBookById(bookId);
        MyBook myBook = new MyBook(0, false);
        myBook.setBook(book);

        bookRepository.saveMyBook(myBook);
        return myBook;
    }
}
