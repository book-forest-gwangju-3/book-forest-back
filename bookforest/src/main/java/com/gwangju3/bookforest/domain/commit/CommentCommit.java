package com.gwangju3.bookforest.domain.commit;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;

@Entity
@Getter
@DiscriminatorValue("COMMENT")
public class CommentCommit extends Commit {

}
