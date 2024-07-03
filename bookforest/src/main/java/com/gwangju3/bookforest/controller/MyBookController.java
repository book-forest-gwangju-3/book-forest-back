package com.gwangju3.bookforest.controller;

import com.gwangju3.bookforest.domain.MyBook;
import com.gwangju3.bookforest.dto.MessageResponse;
import com.gwangju3.bookforest.dto.book.MyBookDTO;
import com.gwangju3.bookforest.dto.book.UpdateMyBookRequest;
import com.gwangju3.bookforest.mapper.MyBookMapper;
import com.gwangju3.bookforest.service.MyBookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/books/{bookId}")
@RestController
@RequiredArgsConstructor
public class MyBookController {

    private final MyBookService myBookService;

    // 독서 시작 (MyBook 엔티티 생성)
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/start")
    public MyBookDTO createMyBook(@PathVariable("bookId") String bookId) {
        MyBook myBook = myBookService.createMyBook(Long.parseLong(bookId));
        return MyBookMapper.entityToDTO(myBook);
    }


    // 독서 페이지 기록 (MyBook 엔티티의 lastReadPage 수정)
    @PatchMapping("/read")
    public ResponseEntity<Object> updateMyBook(
            @PathVariable("bookId") String bookId,
            @RequestBody @Valid UpdateMyBookRequest request
    ) {
        MyBook myBook = myBookService.updateMyBook(Long.parseLong(bookId), request.getPage());
        if (myBook != null) {
            return new ResponseEntity<>(MyBookMapper.entityToDTO(myBook), HttpStatus.OK);
        } else {
            MessageResponse message = new MessageResponse("입력할 페이지의 수는 책의 마지막 페이지보다 작고, 마지막 읽었던 페이지보다 많아야 합니다.");
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
    }
}
