package com.gwangju3.bookforest.dto.comment;

import com.gwangju3.bookforest.dto.bookreview.BookReviewDTO;
import com.gwangju3.bookforest.dto.user.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
@AllArgsConstructor
public class CommentDTO {
    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UserDTO user;
}
