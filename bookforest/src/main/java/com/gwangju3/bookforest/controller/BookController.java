package com.gwangju3.bookforest.controller;

import com.gwangju3.bookforest.domain.Book;
import com.gwangju3.bookforest.domain.MyBook;
import com.gwangju3.bookforest.dto.CreateUserRequest;
import com.gwangju3.bookforest.dto.book.BookDTO;
import com.gwangju3.bookforest.dto.book.MyBookDTO;
import com.gwangju3.bookforest.dto.book.ReadBookListResponse;
import com.gwangju3.bookforest.dto.book.UpdateMyBookRequest;
import com.gwangju3.bookforest.mapper.BookMapper;
import com.gwangju3.bookforest.mapper.MyBookMapper;
import com.gwangju3.bookforest.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
                .map(BookMapper::entityToDTO)
                .collect(Collectors.toList());

        return new ReadBookListResponse(items);
    }


    // 독서 시작 (MyBook 엔티티 생성)
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/books/{bookId}/start")
    public MyBookDTO createMyBook(@PathVariable("bookId") String bookId) {
        MyBook myBook = bookService.createMyBook(Long.parseLong(bookId));
        return MyBookMapper.entityToDTO(myBook);
    }


    // 독서 페이지 기록 (MyBook 엔티티의 lastReadPage 수정)
    @PostMapping("/books/{bookId}/read")
    public MyBookDTO updateMyBook(
            @PathVariable("bookId") String bookId,
            @RequestBody @Valid UpdateMyBookRequest request
    ) {
        MyBook myBook = bookService.updateMyBook(Long.parseLong(bookId), request.getPage());
        return MyBookMapper.entityToDTO(myBook);
    }

}
