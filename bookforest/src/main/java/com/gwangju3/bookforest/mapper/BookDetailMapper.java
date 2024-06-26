package com.gwangju3.bookforest.mapper;

import com.gwangju3.bookforest.domain.Book;
import com.gwangju3.bookforest.domain.MyBook;
import com.gwangju3.bookforest.dto.book.QuickReviewDTO;
import com.gwangju3.bookforest.dto.book.ReadBookDetailResponse;
import com.gwangju3.bookforest.dto.book.ReadStatusDTO;
import com.gwangju3.bookforest.dto.user.UserDTO;
import com.gwangju3.bookforest.dto.user.UserRankingDTO;

import java.util.List;
import java.util.stream.Collectors;

public class BookDetailMapper {
    public static ReadBookDetailResponse entityToDTO(Book book, MyBook myBook) {
        List<QuickReviewDTO> quickReviewDTOS = book.getQuickReviews().stream()
                .map(QuickReviewMapper::entityToDTO)
                .collect(Collectors.toList());

        List<UserDTO> likedUserDTOS = book.getBookLikes().stream()
                .map(o -> UserMapper.entityToDTO(o.getUser()))
                .collect(Collectors.toList());

        if (myBook == null) {
            return new ReadBookDetailResponse(
                    BookMapper.entityToDTO(book),
                    null,
                    quickReviewDTOS,
                    likedUserDTOS
            );
        }

        return new ReadBookDetailResponse(
                BookMapper.entityToDTO(book),
                new ReadStatusDTO(myBook.getLastReadPage(), myBook.getReadCompleted()),
                quickReviewDTOS,
                likedUserDTOS
        );
    }
}
