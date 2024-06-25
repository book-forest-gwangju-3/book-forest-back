package com.gwangju3.bookforest.domain;

import com.gwangju3.bookforest.domain.like.BookLike;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;

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

    private LocalDate pubdate;

    private String description;

    private String coverUrl;

    private Integer bestRank;

    private Integer page;

    protected Book() {

    }

    public Book(Long id, String title, String author, LocalDate pubdate, String description, String coverUrl, Integer bestRank, Integer page) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.pubdate = pubdate;
        this.description = description;
        this.coverUrl = coverUrl;
        this.bestRank = bestRank;
        this.page = page;
    }

    // TODO: QuickReview, BookLike 목록
    @OneToMany(mappedBy = "book")
    private List<QuickReview> quickReviews = new ArrayList<>();

    @OneToMany(mappedBy = "book")
    private List<BookLike> bookLikes = new ArrayList<>();
}
