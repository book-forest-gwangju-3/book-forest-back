package com.gwangju3.bookforest.controller;

import com.gwangju3.bookforest.domain.BookReview;
import com.gwangju3.bookforest.dto.UpdateBookReviewRequest;
import com.gwangju3.bookforest.dto.bookreview.BookReviewDTO;
import com.gwangju3.bookforest.dto.bookreview.CreateBookReviewRequest;
import com.gwangju3.bookforest.dto.bookreview.ReadBookReviewListResponse;
import com.gwangju3.bookforest.dto.bookreview.ReadBookReviewResponse;
import com.gwangju3.bookforest.mapper.BookReviewMapper;
import com.gwangju3.bookforest.service.BookReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class BookReviewController {

    private final BookReviewService bookReviewService;

    @GetMapping("/book-reviews")
    public ReadBookReviewListResponse bookReviews() {
        List<BookReview> bookReviews = bookReviewService.findAll();
        List<BookReviewDTO> collect = bookReviews.stream()
                .map(BookReviewMapper::toDto)
                .collect(Collectors.toList());

        return new ReadBookReviewListResponse(collect, collect.size());
    }

    @GetMapping("/book-reviews/{bookReviewId}")
    public ReadBookReviewResponse bookReview(@PathVariable("bookReviewId") Long id) {
        BookReview bookReview = bookReviewService.findBook(id);
        BookReviewDTO bookReviewDTO = BookReviewMapper.toDto(bookReview);

        return new ReadBookReviewResponse(bookReviewDTO);
    }

    @PostMapping("/book-reviews")
    public ReadBookReviewResponse createBookReview(@RequestBody @Valid CreateBookReviewRequest request) {
        System.out.println(request.getTitle());
        Long id = bookReviewService.createBookReview(request);
        BookReview bookReview = bookReviewService.findBook(id);

        BookReviewDTO bookReviewDTO = BookReviewMapper.toDto(bookReview);

        return new ReadBookReviewResponse(bookReviewDTO);
    }

    @PatchMapping("/book-reviews/{bookReviewId}")
    public ReadBookReviewResponse updateBookReview(@PathVariable("bookReviewId") Long bookReviewId, @RequestBody UpdateBookReviewRequest request) {
        bookReviewService.updateBook(request, bookReviewId);
        BookReview bookReview = bookReviewService.findBook(bookReviewId);

        BookReviewDTO bookReviewDTO = BookReviewMapper.toDto(bookReview);
        return new ReadBookReviewResponse(bookReviewDTO);
    }
}

