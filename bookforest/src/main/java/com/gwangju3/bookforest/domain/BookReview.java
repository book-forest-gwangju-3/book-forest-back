package com.gwangju3.bookforest.domain;

import com.gwangju3.bookforest.domain.like.BookLike;
import com.gwangju3.bookforest.domain.like.BookReviewLike;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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

    protected BookReview() {
    }

    public BookReview(Book book, String title, String content) {
        this.book = book;
        this.title = title;
        this.content = content;
    }

    // TODO: like
    @OneToMany(mappedBy = "bookReview")
    private List<Comment> comments = new ArrayList<>();

    public void setUser(User user) {
        this.user = user;
        getUser().getBookReviews().add(this);
    }

    @OneToMany(mappedBy = "bookReview")
    private List<BookReviewLike> bookReviewLikes = new ArrayList<>();
}

