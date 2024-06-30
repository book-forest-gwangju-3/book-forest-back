package com.gwangju3.bookforest.domain;

import com.gwangju3.bookforest.domain.commit.CommentCommit;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
public class Comment {

    @Id
    @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_review_id")
    private BookReview bookReview;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String content;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToOne(mappedBy = "comment", cascade = CascadeType.ALL)
    private CommentCommit commentCommit;

    protected Comment() {
    }

    public Comment(User user, String content) {
        this.user = user;
        this.content = content;
    }

    public void setBookReview(BookReview bookReview) {
        this.bookReview = bookReview;
        getBookReview().getComments().add(this);
    }

    public void updateContent(String content) {
        this.content = content;
    }
}
