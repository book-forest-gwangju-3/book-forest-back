package com.gwangju3.bookforest.service;

import com.gwangju3.bookforest.domain.Book;
import com.gwangju3.bookforest.domain.QuickReview;
import com.gwangju3.bookforest.domain.User;
import com.gwangju3.bookforest.dto.book.CreateQuickReviewRequest;
import com.gwangju3.bookforest.dto.book.DeleteQuickReviewRequest;
import com.gwangju3.bookforest.dto.book.UpdateQuickReviewRequest;
import com.gwangju3.bookforest.repository.BookRepository;
import com.gwangju3.bookforest.repository.QuickReviewRepository;
import com.gwangju3.bookforest.repository.UserRepository;
import com.gwangju3.bookforest.util.UserUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class QuickReviewService {

    private final UserRepository userRepository;
    private final QuickReviewRepository quickReviewRepository;
    private final BookRepository bookRepository;

    public QuickReview createQuickReview(CreateQuickReviewRequest request) {
        String username = UserUtil.extractUsername();
        User user = userRepository.findByUsername(username).get(0);

        Book book = bookRepository.findBookById(request.getBookId());

        QuickReview quickReview = new QuickReview(user, request.getContent());
        quickReview.setBook(book);

        quickReviewRepository.saveQuickReview(quickReview);
        return quickReview;
    }

    public QuickReview updateQuickReview(UpdateQuickReviewRequest request) {
        QuickReview quickReview = quickReviewRepository.findQuickReviewById(request.getQuickReviewId());

        String username = UserUtil.extractUsername();
        User user = userRepository.findByUsername(username).get(0);

        if (quickReview.getUser().getId().equals(user.getId())) {
            quickReview.setContent(request.getContent());
            return quickReview;
        } else {
            return null;
        }
    }

    public Boolean deleteQuickReview(DeleteQuickReviewRequest request) {
        QuickReview quickReview = quickReviewRepository.findQuickReviewById(request.getQuickReviewId());

        String username = UserUtil.extractUsername();
        User user = userRepository.findByUsername(username).get(0);

        if (quickReview.getUser().getId().equals(user.getId())) {
            quickReviewRepository.deleteQuickReview(quickReview);
            return true;
        } else {
            return false;
        }
    }
}
