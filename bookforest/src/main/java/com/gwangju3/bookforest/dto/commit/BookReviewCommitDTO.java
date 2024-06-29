package com.gwangju3.bookforest.dto.commit;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class BookReviewCommitDTO extends CommitDTO {
    private Long bookReviewId;
    private String bookReviewTitle;

    public BookReviewCommitDTO(String commitType, Integer exp, Long bookReviewId, String bookReviewTitle) {
        super(commitType, exp);
        this.bookReviewId = bookReviewId;
        this.bookReviewTitle = bookReviewTitle;
    }
}
