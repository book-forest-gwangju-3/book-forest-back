package com.gwangju3.bookforest.mapper;

import com.gwangju3.bookforest.domain.BookReview;
import com.gwangju3.bookforest.domain.commit.BookReviewCommit;
import com.gwangju3.bookforest.domain.commit.CommentCommit;
import com.gwangju3.bookforest.domain.commit.Commit;
import com.gwangju3.bookforest.domain.commit.ReadCommit;
import com.gwangju3.bookforest.dto.commit.BookReviewCommitDTO;
import com.gwangju3.bookforest.dto.commit.CommentCommitDTO;
import com.gwangju3.bookforest.dto.commit.CommitDTO;
import com.gwangju3.bookforest.dto.commit.ReadCommitDTO;

public class CommitMapper {

    public static CommitDTO toDTO(Commit commit) {
        if (commit.getClass().equals(CommentCommit.class)) {
            return new CommentCommitDTO(
                    "Comment",
                    commit.getExp(),
                    ((CommentCommit) commit).getComment().getBookReview().getId(),
                    ((CommentCommit) commit).getComment().getContent()
            );
        } else if (commit.getClass().equals(BookReviewCommit.class)) {
            return new BookReviewCommitDTO(
                    "BookReview",
                    commit.getExp(),
                    ((BookReviewCommit) commit).getBookReview().getId(),
                    ((BookReviewCommit) commit).getBookReview().getTitle()
            );
        } else if (commit.getClass().equals(ReadCommit.class)) {
            return new ReadCommitDTO(
                    "Read",
                    commit.getExp(),
                    ((ReadCommit) commit).getMyBook().getBook().getId(),
                    ((ReadCommit) commit).getMyBook().getBook().getTitle(),
                    ((ReadCommit) commit).getReadPages()
            );
        }

        return null;
    }
}
