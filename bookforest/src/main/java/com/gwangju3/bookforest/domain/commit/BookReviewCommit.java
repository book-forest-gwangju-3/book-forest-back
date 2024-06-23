package com.gwangju3.bookforest.domain.commit;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;

@Entity
@Getter
@DiscriminatorValue("BOOK_REVIEW")
public class BookReviewCommit extends Commit {

}
