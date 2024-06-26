package com.gwangju3.bookforest.dto.bookreview;


import com.gwangju3.bookforest.domain.BookReview;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ReadBookReviewResponse {
    private BookReview bookReview;
}
