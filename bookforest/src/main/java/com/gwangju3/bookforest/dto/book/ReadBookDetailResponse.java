package com.gwangju3.bookforest.dto.book;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ReadBookDetailResponse {
    private BookDTO book;
    private ReadStatusDTO myReadStatus;
    private List<QuickReviewDTO> quickReviews;
}
