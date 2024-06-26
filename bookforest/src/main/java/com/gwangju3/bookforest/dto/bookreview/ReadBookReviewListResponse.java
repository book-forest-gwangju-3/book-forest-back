package com.gwangju3.bookforest.dto.bookreview;


import com.gwangju3.bookforest.domain.BookReview;
import com.gwangju3.bookforest.dto.user.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ReadBookReviewListResponse {
    private List<BookReviewDTO> bookReviews;
    private long bookReviewsCount;
}
