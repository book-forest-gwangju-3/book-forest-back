package com.gwangju3.bookforest.dto.book;

import com.gwangju3.bookforest.dto.user.UserDTO;
import com.gwangju3.bookforest.dto.user.UserRankingDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class QuickReviewDTO {
    private Long quickReviewId;
    private UserDTO user;
    private Long bookId;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
