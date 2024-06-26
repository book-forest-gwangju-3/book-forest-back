package com.gwangju3.bookforest.controller;

import com.gwangju3.bookforest.domain.BookReview;
import com.gwangju3.bookforest.dto.bookreview.ReadBookReviewListResponse;
import com.gwangju3.bookforest.service.BookReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
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
}

