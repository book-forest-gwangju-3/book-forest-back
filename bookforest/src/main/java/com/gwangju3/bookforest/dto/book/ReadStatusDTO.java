package com.gwangju3.bookforest.dto.book;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReadStatusDTO {
    private Integer lastReadPage;
    private Boolean readCompleted;
}
