package com.gwangju3.bookforest.service;

import com.gwangju3.bookforest.domain.BookReview;
import com.gwangju3.bookforest.repository.BookReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BookReviewService {

    private final BookReviewRepository bookReviewRepository;

    public List<BookReview> findAll() {
        return bookReviewRepository.findAll();
    }
}
