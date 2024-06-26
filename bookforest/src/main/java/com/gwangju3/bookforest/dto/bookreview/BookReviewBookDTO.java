package com.gwangju3.bookforest.dto.bookreview;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BookReviewBookDTO {
    private Long id;
    private String title;
    private String author;
    private String cover;
}
