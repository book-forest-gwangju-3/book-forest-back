package com.gwangju3.bookforest.controller;

import com.gwangju3.bookforest.domain.MyBook;
import com.gwangju3.bookforest.domain.User;
import com.gwangju3.bookforest.dto.CreateUserRequest;
import com.gwangju3.bookforest.dto.MessageResponse;
import com.gwangju3.bookforest.dto.book.BookDTO;
import com.gwangju3.bookforest.dto.book.ReadBookListResponse;
import com.gwangju3.bookforest.dto.user.UserDTO;
import com.gwangju3.bookforest.dto.user.UserRankingDTO;
import com.gwangju3.bookforest.mapper.BookMapper;
import com.gwangju3.bookforest.mapper.UserMapper;
import com.gwangju3.bookforest.mapper.UserRankingMapper;
import com.gwangju3.bookforest.service.BookService;
import com.gwangju3.bookforest.service.SignUpService;
import com.gwangju3.bookforest.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/ranking")
    public List<UserRankingDTO> sortUserByRanking() {
        List<User> users = userService.sortUserByRanking();
        List<UserRankingDTO> result = users.stream()
                .map(UserRankingMapper::entityToDTO)
                .collect(Collectors.toList());
        return result;
    }

    @PostMapping("/signup")
    public ResponseEntity<Object> signUp(@RequestBody @Valid CreateUserRequest request) {
        signUpService.signUp(request);
        MessageResponse messageResponse = new MessageResponse("Welcome");
        return new ResponseEntity<>(messageResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{userId}/books/reading")
    public ResponseEntity<Object> getReadingBookList(
            @PathVariable("userId") String userId
    ) {
        List<MyBook> readingBooks = bookService.findReadingBookListByUserId(Long.parseLong(userId));
        if (readingBooks == null) {
            MessageResponse messageResponse = new MessageResponse("독서를 기록한 적이 없습니다.");
            return new ResponseEntity<>(messageResponse, HttpStatus.OK);
        } else if (readingBooks.isEmpty()) {
            MessageResponse messageResponse = new MessageResponse("현재 독서 진행중인 책이 없습니다.");
            return new ResponseEntity<>(messageResponse, HttpStatus.OK);
        } else {
            List<BookDTO> items = readingBooks.stream()
                    .map(o -> BookMapper.entityToDTO(o.getBook()))
                    .collect(Collectors.toList());
            return new ResponseEntity<>(new ReadBookListResponse(items), HttpStatus.OK);
        }
    }

    @GetMapping("/{userId}/books/completed")
    public ResponseEntity<Object> getCompletedBookList(
            @PathVariable("userId") String userId
    ) {
        List<MyBook> readingBooks = bookService.findCompletedBookListByUserId(Long.parseLong(userId));
        if (readingBooks == null) {
            MessageResponse messageResponse = new MessageResponse("독서를 기록한 적이 없습니다.");
            return new ResponseEntity<>(messageResponse, HttpStatus.OK);
        } else if (readingBooks.isEmpty()) {
            MessageResponse messageResponse = new MessageResponse("아직 독서를 완료한 책이 없습니다.");
            return new ResponseEntity<>(messageResponse, HttpStatus.OK);
        } else {
            List<BookDTO> items = readingBooks.stream()
                    .map(o -> BookMapper.entityToDTO(o.getBook()))
                    .collect(Collectors.toList());
            return new ResponseEntity<>(new ReadBookListResponse(items), HttpStatus.OK);
        }
    }
}
