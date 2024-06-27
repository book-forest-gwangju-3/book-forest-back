package com.gwangju3.bookforest.mapper;

import com.gwangju3.bookforest.domain.QuickReview;
import com.gwangju3.bookforest.dto.book.QuickReviewDTO;

public class QuickReviewMapper {
    public static QuickReviewDTO entityToDTO(QuickReview quickReview) {
        return new QuickReviewDTO(
                quickReview.getId(),
                UserMapper.entityToDTO(quickReview.getUser()),
                quickReview.getBook().getId(),
                quickReview.getContent(),
                quickReview.getCreatedAt(),
                quickReview.getUpdatedAt()
        );
    }

}
