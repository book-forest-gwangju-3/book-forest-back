package com.gwangju3.bookforest.repository;

import com.gwangju3.bookforest.domain.QuickReview;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class QuickReviewRepository {

    private final EntityManager em;

    public void saveQuickReview(QuickReview quickReview) {
        em.persist(quickReview);
    }

    public QuickReview findQuickReviewById(Long quickReviewId) {
        return em.find(QuickReview.class, quickReviewId);
    }

    public void deleteQuickReview(QuickReview quickReview) {
        em.remove(quickReview);
    }
}
