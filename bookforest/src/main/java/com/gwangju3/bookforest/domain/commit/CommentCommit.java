package com.gwangju3.bookforest.domain.commit;

import com.gwangju3.bookforest.domain.BookReview;
import com.gwangju3.bookforest.domain.Comment;
import com.gwangju3.bookforest.domain.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Entity
@Getter
@DiscriminatorValue("COMMENT")
public class CommentCommit extends Commit {

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    public CommentCommit(Integer exp, Comment comment) {
        super(exp);
        this.comment = comment;
    }

    protected CommentCommit() {

    }
}
