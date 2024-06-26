package com.gwangju3.bookforest.controller;

import com.gwangju3.bookforest.domain.Book;
import com.gwangju3.bookforest.domain.MyBook;
import com.gwangju3.bookforest.domain.QuickReview;
import com.gwangju3.bookforest.dto.MessageResponse;
import com.gwangju3.bookforest.dto.book.*;
import com.gwangju3.bookforest.mapper.BookDetailMapper;
import com.gwangju3.bookforest.mapper.BookMapper;
import com.gwangju3.bookforest.mapper.MyBookMapper;
import com.gwangju3.bookforest.mapper.QuickReviewMapper;
import com.gwangju3.bookforest.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/books")
@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;


    /*
    * 책 조회
    * */

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
        MyBook myBook = bookService.findMyBookByUserBook(Long.parseLong(bookId));

        return BookDetailMapper.entityToDTO(book, myBook);
    }

    /*
     * 독서 (MyBook)
     * */


    // 독서 시작 (MyBook 엔티티 생성)
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{bookId}/start")
    public MyBookDTO createMyBook(@PathVariable("bookId") String bookId) {
        MyBook myBook = bookService.createMyBook(Long.parseLong(bookId));
        return MyBookMapper.entityToDTO(myBook);
    }


    // 독서 페이지 기록 (MyBook 엔티티의 lastReadPage 수정)
    @PatchMapping("/{bookId}/read")
    public ResponseEntity<Object> updateMyBook(
            @PathVariable("bookId") String bookId,
            @RequestBody @Valid UpdateMyBookRequest request
    ) {
        MyBook myBook = bookService.updateMyBook(Long.parseLong(bookId), request.getPage());
        if (myBook != null) {
            return new ResponseEntity<>(MyBookMapper.entityToDTO(myBook), HttpStatus.OK);
        } else {
            MessageResponse message = new MessageResponse("입력할 페이지의 수는 책의 마지막 페이지보다 작고, 마지막 읽었던 페이지보다 많아야 합니다.");
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
    }



    /*
     * 한줄평
     * */


    // 한줄평 생성
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/quick-reviews")
    public QuickReviewDTO createQuickReview(
            @RequestBody @Valid CreateQuickReviewRequest request
    ) {
        QuickReview quickReview = bookService.createQuickReview(request);
        return QuickReviewMapper.entityToDTO(quickReview);
    }


    // 한줄평 수정
    @PatchMapping("/quick-reviews")
    public ResponseEntity<Object> updateQuickReview(
            @RequestBody @Valid UpdateQuickReviewRequest request
    ) {
        QuickReview quickReview = bookService.updateQuickReview(request);

        if (quickReview != null) {
            return new ResponseEntity<>(QuickReviewMapper.entityToDTO(quickReview), HttpStatus.OK);
        } else {
            MessageResponse message = new MessageResponse("작성자만 수정이 가능합니다.");
            return new ResponseEntity<>(message, HttpStatus.FORBIDDEN);
        }
    }


    // 한줄평 삭제
    @DeleteMapping("/quick-reviews")
    public ResponseEntity<Object> deleteQuickReview(
            @RequestBody @Valid DeleteQuickReviewRequest request
    ) {
        Boolean didDelete = bookService.deleteQuickReview(request);

        if (didDelete) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            MessageResponse message = new MessageResponse("작성자만 삭제가 가능합니다.");
            return new ResponseEntity<>(message, HttpStatus.FORBIDDEN);
        }
    }



    /*
     * 찜
     * */

    // 찜하기 & 취소
    @PostMapping("/like")
    public ResponseEntity<Object> toggleBookLike(
            @RequestBody @Valid CreateBookLikeRequest request
    ) {
        boolean didCreate = bookService.toggleBookLike(request);
        return (didCreate) ? new ResponseEntity<>(HttpStatus.CREATED) : new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
