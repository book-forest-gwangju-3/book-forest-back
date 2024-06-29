package com.gwangju3.bookforest.dto.commit;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommitDTO {
    private String commitType;
    private Integer exp;
}
