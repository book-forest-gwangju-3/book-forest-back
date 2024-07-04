package com.gwangju3.bookforest.controller;

import com.gwangju3.bookforest.domain.Book;
import com.gwangju3.bookforest.domain.MyBook;
import com.gwangju3.bookforest.dto.AladinAPIRequest;
import com.gwangju3.bookforest.dto.MessageResponse;
import com.gwangju3.bookforest.dto.book.*;
import com.gwangju3.bookforest.exception.book.InvalidPageException;
import com.gwangju3.bookforest.mapper.BookDetailMapper;
import com.gwangju3.bookforest.mapper.BookMapper;
import com.gwangju3.bookforest.service.BookService;
import com.gwangju3.bookforest.service.MyBookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/books")
@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final MyBookService myBookService;


    // 책 전체 목록 조회
    @GetMapping("")
    public ReadBookListResponse books(
            @RequestParam(defaultValue = "") String q
    ) {
        List<Book> allBooks = bookService.searchBook(q);

        List<BookDTO> items = allBooks.stream()
                .map(BookMapper::entityToDTO)
                .collect(Collectors.toList());

        return new ReadBookListResponse(items);
    }


    // 책 상세 조회
    @GetMapping("/{bookId}")
    public ReadBookDetailResponse book(@PathVariable("bookId") String bookId) {
        Book book = bookService.findBookById(Long.parseLong(bookId));
        MyBook myBook = myBookService.findMyBookByUserBook(Long.parseLong(bookId));
        return BookDetailMapper.entityToDTO(book, myBook);
    }


    // 책 찜하기 & 취소
    @PostMapping("/like")
    public ResponseEntity<Object> toggleBookLike(
            @RequestBody @Valid CreateBookLikeRequest request
    ) {
        boolean didCreate = bookService.toggleBookLike(request);
        return (didCreate) ? new ResponseEntity<>(HttpStatus.CREATED) : new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    /*
    *
    * 테마별 책 리스트 조회
    *
    * */

    @GetMapping("/best")
    public ReadBookListResponse saveBestSeller(
            @RequestBody @Valid AladinAPIRequest request
    ) throws IOException, URISyntaxException {
        List<Book> bestSellerList = bookService.findBestSeller(request);
        List<BookDTO> items = bestSellerList.stream()
                .map(BookMapper::entityToDTO)
                .collect(Collectors.toList());
        return new ReadBookListResponse(items);
    }

  
    @GetMapping("/new-all")
    public ReadBookListResponse saveNewAll(
            @RequestBody @Valid AladinAPIRequest request
    ) throws IOException, URISyntaxException {
        List<Book> bestSellerList = bookService.findNewAll(request);
        List<BookDTO> items = bestSellerList.stream()
                .map(BookMapper::entityToDTO)
                .collect(Collectors.toList());
        return new ReadBookListResponse(items);
    }

    @GetMapping("/new-special")
    public ReadBookListResponse saveNewSpecial(
            @RequestBody @Valid AladinAPIRequest request
    ) throws IOException, URISyntaxException {
        List<Book> bestSellerList = bookService.findNewSpecial(request);
        List<BookDTO> items = bestSellerList.stream()
                .map(BookMapper::entityToDTO)
                .collect(Collectors.toList());
        return new ReadBookListResponse(items);
    }

    @GetMapping("/editor")
    public ReadBookListResponse saveEditorChoice(
            @RequestBody @Valid AladinAPIRequest request
    ) throws IOException, URISyntaxException {
        List<Book> bestSellerList = bookService.findEditorChoice(request);
        List<BookDTO> items = bestSellerList.stream()
                .map(BookMapper::entityToDTO)
                .collect(Collectors.toList());
        return new ReadBookListResponse(items);
    }
}
