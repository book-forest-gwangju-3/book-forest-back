package com.gwangju3.bookforest.dto.book;

import com.gwangju3.bookforest.domain.like.BookLike;
import com.gwangju3.bookforest.dto.user.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ReadBookDetailResponse {
    private BookDTO book;
    private ReadStatusDTO myReadStatus;
    private List<QuickReviewDTO> quickReviews;
    private List<UserDTO> likedUsers;
}
