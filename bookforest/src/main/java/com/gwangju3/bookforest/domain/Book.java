package com.gwangju3.bookforest.domain;

import com.gwangju3.bookforest.domain.like.BookLike;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Book {

    @Id
    @Column(name = "book_id")
    private Long id;

    private String title;

    private String author;

    private LocalDate pubDate;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String coverUrl;

    @Nullable @Setter
    private Integer bestRank;

    private Integer page;

    private Integer standardPrice;

    private String publisher;

    private String categoryName;

    protected Book() {

    }

    // bestRank 포함
    public Book(Long id, String title, String author, LocalDate pubDate, String description, String coverUrl, Integer bestRank, Integer page, Integer standardPrice, String publisher, String categoryName) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.pubDate = pubDate;
        this.description = description;
        this.coverUrl = coverUrl;
        this.bestRank = bestRank;
        this.page = page;
        this.standardPrice = standardPrice;
        this.publisher = publisher;
        this.categoryName = categoryName;
    }

    // bestRank 미포함
    public Book(Long id, String title, String author, LocalDate pubDate, String description, String coverUrl, Integer page, Integer standardPrice, String publisher, String categoryName) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.pubDate = pubDate;
        this.description = description;
        this.coverUrl = coverUrl;
        this.page = page;
        this.standardPrice = standardPrice;
        this.publisher = publisher;
        this.categoryName = categoryName;
    }


    // TODO: QuickReview, BookLike 목록
    @OneToMany(mappedBy = "book")
    private List<QuickReview> quickReviews = new ArrayList<>();

    @OneToMany(mappedBy = "book")
    private List<BookLike> bookLikes = new ArrayList<>();
}
