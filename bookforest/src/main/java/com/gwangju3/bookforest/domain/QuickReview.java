package com.gwangju3.bookforest.domain;

import com.gwangju3.bookforest.util.UserUtil;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
public class QuickReview {
    @Id
    @GeneratedValue
    @Column(name = "quick_review_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @Setter
    private String content;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    protected QuickReview() {
    }

    public QuickReview(User user, String content) {
        this.user = user;
        this.content = content;
    }

    public void setBook(Book book) {
        this.book = book;
        getBook().getQuickReviews().add(this);
    }
}
