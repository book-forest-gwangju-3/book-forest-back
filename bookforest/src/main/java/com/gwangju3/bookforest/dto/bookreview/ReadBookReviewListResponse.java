package com.gwangju3.bookforest.dto.bookreview;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ReadBookReviewListResponse {
    private List<BookReviewDTO> bookReviews;
    private long bookReviewsCount;
}
