package com.gwangju3.bookforest.domain.commit;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;

@Entity
@Getter
@DiscriminatorValue("READ")
public class ReadCommit extends Commit {
    private Integer startPage;
    private Integer endPage;
}
