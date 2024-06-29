package com.gwangju3.bookforest.dto.commit;

import lombok.Getter;

@Getter
public class CommentCommitDTO extends CommitDTO{
    private Long bookReviewId;
    private String content;

    public CommentCommitDTO(String commitType, Integer exp, Long bookReviewId, String content) {
        super(commitType, exp);
        this.bookReviewId = bookReviewId;
        this.content = content;
    }
}
