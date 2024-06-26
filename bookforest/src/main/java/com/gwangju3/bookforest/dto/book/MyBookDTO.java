package com.gwangju3.bookforest.dto.book;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MyBookDTO {
    private Integer lastReadPage;
    private Boolean readCompleted;
    private BookDTO book;
}
