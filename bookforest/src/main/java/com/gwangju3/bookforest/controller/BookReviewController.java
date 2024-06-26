package com.gwangju3.bookforest.controller;

import com.gwangju3.bookforest.domain.BookReview;
import com.gwangju3.bookforest.dto.bookreview.ReadBookReviewListResponse;
import com.gwangju3.bookforest.dto.bookreview.ReadBookReviewResponse;
import com.gwangju3.bookforest.service.BookReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookReviewController {

    private final BookReviewService bookReviewService;

    @GetMapping("/book-reviews")
    public ReadBookReviewListResponse bookReviews() {
        List<BookReview> bookReviews = bookReviewService.findAll();
        return new ReadBookReviewListResponse(bookReviews);
    }

    @GetMapping("/book-reviews/{book_review_id}")
    public ReadBookReviewResponse bookReview(@PathVariable("book_review_id") Long id) {
        BookReview bookReview = bookReviewService.findBook(id);
        return new ReadBookReviewResponse(bookReview);
    }
}

