package com.gwangju3.bookforest.dto.book;

import com.gwangju3.bookforest.domain.Book;
import com.gwangju3.bookforest.domain.User;
import com.gwangju3.bookforest.dto.user.UserDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
