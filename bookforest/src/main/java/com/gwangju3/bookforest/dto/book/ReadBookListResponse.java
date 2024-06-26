package com.gwangju3.bookforest.dto.book;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class ReadBookListResponse {
    public List<BookDTO> items;
}
