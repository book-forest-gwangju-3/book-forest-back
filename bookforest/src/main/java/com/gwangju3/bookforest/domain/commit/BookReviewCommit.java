package com.gwangju3.bookforest.domain.commit;

import com.gwangju3.bookforest.domain.BookReview;
import com.gwangju3.bookforest.domain.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;

@Entity
@Getter
@DiscriminatorValue("BOOK_REVIEW")
public class BookReviewCommit extends Commit {

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "book_review_id")
    private BookReview bookReview;

    public BookReviewCommit(Integer exp, BookReview bookReview) {
        super(exp);
        this.bookReview = bookReview;
    }

    protected BookReviewCommit() {

    }
}
