package com.gwangju3.bookforest.mapper;

import com.gwangju3.bookforest.domain.MyBook;
import com.gwangju3.bookforest.dto.book.MyBookDTO;

public class MyBookMapper {
    public static MyBookDTO entityToDTO(MyBook myBook) {
        if (myBook == null) return null;

        return new MyBookDTO(
                myBook.getLastReadPage(),
                myBook.getReadCompleted(),
                BookMapper.entityToDTO(myBook.getBook())
        );
    }
}
