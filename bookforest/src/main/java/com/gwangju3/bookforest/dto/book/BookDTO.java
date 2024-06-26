package com.gwangju3.bookforest.dto.book;

import com.gwangju3.bookforest.domain.Book;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class BookDTO {
    private Long id;

    private String title;

    private String author;

    private LocalDate pubDate;

    private String description;

    private String coverUrl;

    private Integer bestRank;

    private Integer page;

    private Integer standardPrice;

    private String publisher;

    private String categoryName;
}
