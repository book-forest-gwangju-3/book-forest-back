package com.gwangju3.bookforest.controller;

import com.gwangju3.bookforest.domain.QuickReview;
import com.gwangju3.bookforest.dto.MessageResponse;
import com.gwangju3.bookforest.dto.book.CreateQuickReviewRequest;
import com.gwangju3.bookforest.dto.book.DeleteQuickReviewRequest;
import com.gwangju3.bookforest.dto.book.QuickReviewDTO;
import com.gwangju3.bookforest.dto.book.UpdateQuickReviewRequest;
import com.gwangju3.bookforest.mapper.QuickReviewMapper;
import com.gwangju3.bookforest.service.QuickReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/books/quick-reviews")
@RestController
@RequiredArgsConstructor
public class QuickReviewController {

    private final QuickReviewService quickReviewService;

    // 한줄평 생성
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public QuickReviewDTO createQuickReview(
            @RequestBody @Valid CreateQuickReviewRequest request
    ) {
        QuickReview quickReview = quickReviewService.createQuickReview(request);
        return QuickReviewMapper.entityToDTO(quickReview);
    }


    // 한줄평 수정
    @PatchMapping("")
    public ResponseEntity<Object> updateQuickReview(
            @RequestBody @Valid UpdateQuickReviewRequest request
    ) {
        QuickReview quickReview = quickReviewService.updateQuickReview(request);

        if (quickReview != null) {
            return new ResponseEntity<>(QuickReviewMapper.entityToDTO(quickReview), HttpStatus.OK);
        } else {
            MessageResponse message = new MessageResponse("작성자만 수정이 가능합니다.");
            return new ResponseEntity<>(message, HttpStatus.FORBIDDEN);
        }
    }


    // 한줄평 삭제
    @DeleteMapping("")
    public ResponseEntity<Object> deleteQuickReview(
            @RequestBody @Valid DeleteQuickReviewRequest request
    ) {
        Boolean didDelete = quickReviewService.deleteQuickReview(request);

        if (didDelete) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            MessageResponse message = new MessageResponse("작성자만 삭제가 가능합니다.");
            return new ResponseEntity<>(message, HttpStatus.FORBIDDEN);
        }
    }
}
