package com.gwangju3.bookforest.domain.like;


import com.gwangju3.bookforest.domain.BookReview;
import com.gwangju3.bookforest.domain.User;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Entity
@Getter
@DiscriminatorValue("BOOK_REVIEW")
public class BookReviewLike extends Like{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_review_id")
    private BookReview bookReview;

    public void setBookReview(BookReview bookReview) {
        this.bookReview = bookReview;
        getBookReview().getBookReviewLikes().add(this);
    }

    @Override
    public void setUser(User user) {
        super.setUser(user);
        user.getBookReviewLikes().add(this);
    }
}
