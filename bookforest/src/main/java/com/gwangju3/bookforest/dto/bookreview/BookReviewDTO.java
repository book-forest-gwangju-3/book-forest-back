package com.gwangju3.bookforest.dto.bookreview;

import com.gwangju3.bookforest.dto.book.BookDTO;
import com.gwangju3.bookforest.dto.user.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class BookReviewDTO {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UserDTO user;
    private BookReviewBookDTO book;
}