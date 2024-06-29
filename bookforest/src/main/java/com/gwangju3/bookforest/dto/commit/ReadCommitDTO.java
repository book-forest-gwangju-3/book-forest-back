package com.gwangju3.bookforest.dto.commit;

import lombok.Getter;

@Getter
public class ReadCommitDTO extends CommitDTO {
    private Long bookId;
    private String bookTitle;
    private Integer readPage;

    public ReadCommitDTO(String commitType, Integer exp, Long bookId, String bookTitle, Integer readPage) {
        super(commitType, exp);
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.readPage = readPage;
    }
}
