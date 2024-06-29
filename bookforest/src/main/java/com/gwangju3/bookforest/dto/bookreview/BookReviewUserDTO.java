package com.gwangju3.bookforest.dto.bookreview;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class BookReviewUserDTO {
    private Long id;
    private String title;
    private String content;
    private String cover;
    private LocalDateTime createdAt;
}
