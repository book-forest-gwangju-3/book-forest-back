package com.gwangju3.bookforest.controller;

import com.gwangju3.bookforest.domain.Book;
import com.gwangju3.bookforest.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    // Book 속성 추가 후 BookDTO 작성
//    @GetMapping("/books")
//    public List<BookDTO> books() {
//        List<Book> items = bookService.findAllBooks();
//        return items;
//    }

}
