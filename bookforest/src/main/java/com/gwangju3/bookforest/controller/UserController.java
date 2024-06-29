package com.gwangju3.bookforest.controller;

import com.gwangju3.bookforest.domain.MyBook;
import com.gwangju3.bookforest.dto.CreateUserRequest;
import com.gwangju3.bookforest.dto.book.BookDTO;
import com.gwangju3.bookforest.dto.book.ReadBookListResponse;
import com.gwangju3.bookforest.dto.book.UpdateMyBookRequest;
import com.gwangju3.bookforest.dto.user.UserDTO;
import com.gwangju3.bookforest.mapper.BookMapper;
import com.gwangju3.bookforest.service.BookService;
import com.gwangju3.bookforest.service.SignUpService;
import com.gwangju3.bookforest.service.UserService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final SignUpService signUpService;
    private final BookService bookService;

    @GetMapping("/my-info")
    public UserDTO userinfo() {
        return userService.findMe();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup")
    public String signUp(@RequestBody @Valid CreateUserRequest request) {
        signUpService.signUp(request);
        return "Welcome";
    }

    @GetMapping("/{userId}/books/reading")
    public ReadBookListResponse getReadingBookList(
            @PathVariable("userId") String userId
    ) {
        List<MyBook> readingBooks = bookService.findReadingBookListByUserId(Long.parseLong(userId));
        List<BookDTO> items = readingBooks.stream()
                .map(o -> BookMapper.entityToDTO(o.getBook()))
                .collect(Collectors.toList());

        return new ReadBookListResponse(items);
    }

    @GetMapping("/{userId}/books/completed")
    public ReadBookListResponse getCompletedBookList(
            @PathVariable("userId") String userId
    ) {
        List<MyBook> readingBooks = bookService.findCompletedBookListByUserId(Long.parseLong(userId));
        List<BookDTO> items = readingBooks.stream()
                .map(o -> BookMapper.entityToDTO(o.getBook()))
                .collect(Collectors.toList());

        return new ReadBookListResponse(items);
    }
}
