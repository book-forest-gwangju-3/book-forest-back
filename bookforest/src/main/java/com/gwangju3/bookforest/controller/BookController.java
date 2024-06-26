package com.gwangju3.bookforest.controller;

import com.gwangju3.bookforest.domain.Book;
import com.gwangju3.bookforest.dto.book.BookDTO;
import com.gwangju3.bookforest.dto.book.ReadBookListResponse;
import com.gwangju3.bookforest.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;


    // 책 전체 목록 조회
    @GetMapping("/books")
    public ReadBookListResponse books() {
        List<Book> allBooks = bookService.findAllBooks();

        List<BookDTO> items = allBooks.stream()
                .map(BookDTO::bookEntityToDTO)
                .collect(Collectors.toList());

        return new ReadBookListResponse(items);
    }


    // 독서 시작 (MyBook 엔티티 생성)
//    @PostMapping("/books/{bookId}/start")
//    public String createMyBook() {
//        bookService.createMyBook();
//    }

}
