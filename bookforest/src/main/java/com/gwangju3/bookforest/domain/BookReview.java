package com.gwangju3.bookforest.domain;

import com.gwangju3.bookforest.domain.commit.BookReviewCommit;
import com.gwangju3.bookforest.domain.like.BookLike;
import com.gwangju3.bookforest.domain.like.BookReviewLike;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class BookReview {

    @Id
    @GeneratedValue
    @Column(name = "book_review_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    private String title;

    private String content;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToOne(mappedBy = "bookReview", cascade = CascadeType.ALL)
    private BookReviewCommit bookReviewCommit;

    protected BookReview() {
    }

    public BookReview(Book book, String title, String content) {
        this.book = book;
        this.title = title;
        this.content = content;
    }

    // TODO: like
    @OneToMany(mappedBy = "bookReview", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    public void setUser(User user) {
        this.user = user;
        getUser().getBookReviews().add(this);
    }

    @OneToMany(mappedBy = "bookReview", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookReviewLike> bookReviewLikes = new ArrayList<>();

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}

