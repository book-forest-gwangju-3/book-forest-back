package com.gwangju3.bookforest.mapper;

import com.gwangju3.bookforest.domain.Book;
import com.gwangju3.bookforest.domain.MyBook;
import com.gwangju3.bookforest.dto.book.QuickReviewDTO;
import com.gwangju3.bookforest.dto.book.ReadBookDetailResponse;
import com.gwangju3.bookforest.dto.book.ReadStatusDTO;

import java.util.List;
import java.util.stream.Collectors;

public class BookDetailMapper {
    public static ReadBookDetailResponse entityToDTO(Book book, MyBook myBook) {
        List<QuickReviewDTO> quickReviewDTOS = book.getQuickReviews().stream()
                .map(QuickReviewMapper::entityToDTO)
                .collect(Collectors.toList());

        return new ReadBookDetailResponse(
                BookMapper.entityToDTO(book),
                new ReadStatusDTO(myBook.getLastReadPage(), myBook.getReadCompleted()),
                quickReviewDTOS
        );
    }
}
