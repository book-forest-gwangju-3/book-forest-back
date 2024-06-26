package com.gwangju3.bookforest.repository;

import com.gwangju3.bookforest.domain.BookReview;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BookReviewRepository {
    private final EntityManager em;

    public BookReview findBookReviewById(Long id) {
        return em.find(BookReview.class, id);
    }

    public List<BookReview> findAll() {
        return em.createQuery("select b from BookReview b", BookReview.class)
                .getResultList();
    }
}
