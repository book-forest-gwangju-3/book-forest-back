package com.gwangju3.bookforest.mapper;

import com.gwangju3.bookforest.domain.Book;
import com.gwangju3.bookforest.dto.book.BookDTO;

public class BookMapper {
    public static BookDTO entityToDTO(Book book) {
        return new BookDTO(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getPubDate(),
                book.getDescription(),
                book.getCoverUrl(),
                book.getBestRank(),
                book.getPage(),
                book.getStandardPrice(),
                book.getPublisher(),
                book.getCategoryName()
        );
    }
}
