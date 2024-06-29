package com.gwangju3.bookforest.controller;

import com.gwangju3.bookforest.domain.BookReview;
import com.gwangju3.bookforest.dto.MessageResponse;
import com.gwangju3.bookforest.dto.UpdateBookReviewRequest;
import com.gwangju3.bookforest.dto.bookreview.BookReviewDTO;
import com.gwangju3.bookforest.dto.bookreview.BookReviewDetailDTO;
import com.gwangju3.bookforest.dto.bookreview.CreateBookReviewRequest;
import com.gwangju3.bookforest.dto.bookreview.ReadBookReviewDetailResponse;
import com.gwangju3.bookforest.dto.bookreview.ReadBookReviewListResponse;
import com.gwangju3.bookforest.dto.bookreview.ReadBookReviewResponse;
import com.gwangju3.bookforest.mapper.BookReviewMapper;
import com.gwangju3.bookforest.service.BookReviewService;
import com.gwangju3.bookforest.util.UserUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class BookReviewController {

    private final BookReviewService bookReviewService;

    @GetMapping("/book-reviews")
    public ReadBookReviewListResponse bookReviews() {
        List<BookReview> bookReviews = bookReviewService.findAll();
        String currentUsername = UserUtil.extractUsername();
        List<BookReviewDTO> collect = bookReviews.stream()
                .map(bookReview -> BookReviewMapper.toDTO(bookReview, currentUsername))
                .collect(Collectors.toList());

        return new ReadBookReviewListResponse(collect, collect.size());
    }

    @GetMapping("/book-reviews/{bookReviewId}")
    public ReadBookReviewDetailResponse bookReview(@PathVariable("bookReviewId") Long id) {
        BookReview bookReview = bookReviewService.findBook(id);
        String currentUsername = UserUtil.extractUsername();
        BookReviewDetailDTO bookReviewDetailDTO = BookReviewMapper.toDetailDTO(bookReview, currentUsername);

        return new ReadBookReviewDetailResponse(bookReviewDetailDTO);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/book-reviews")
    public ReadBookReviewResponse createBookReview(@RequestBody @Valid CreateBookReviewRequest request) {
        System.out.println(request.getTitle());
        Long id = bookReviewService.createBookReview(request);
        BookReview bookReview = bookReviewService.findBook(id);
        String currentUsername = UserUtil.extractUsername();

        BookReviewDTO bookReviewDTO = BookReviewMapper.toDTO(bookReview, currentUsername);

        return new ReadBookReviewResponse(bookReviewDTO);
    }

    @PatchMapping("/book-reviews/{bookReviewId}")
    public ReadBookReviewResponse updateBookReview(@PathVariable("bookReviewId") Long bookReviewId, @RequestBody UpdateBookReviewRequest request) {
        bookReviewService.updateBook(request, bookReviewId);
        BookReview bookReview = bookReviewService.findBook(bookReviewId);
        String currentUsername = UserUtil.extractUsername();

        BookReviewDTO bookReviewDTO = BookReviewMapper.toDTO(bookReview, currentUsername);
        return new ReadBookReviewResponse(bookReviewDTO);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/book-reviews/{bookReviewId}")
    public void deleteBookReview(@PathVariable("bookReviewId") Long bookReviewId) {
        bookReviewService.deleteBook(bookReviewId);
    }

    @PostMapping("/book-reviews/{bookReviewId}/likes")
    public MessageResponse createBookReviewLike(@PathVariable("bookReviewId") Long bookReviewId) {
        boolean created = bookReviewService.createBookReviewLike(bookReviewId);

        if (created) {
            return new MessageResponse("좋아요 생성");
        }
        return new MessageResponse("좋아요 취소");
    }
}

