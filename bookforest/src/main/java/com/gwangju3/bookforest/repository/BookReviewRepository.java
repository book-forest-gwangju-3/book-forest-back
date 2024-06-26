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
        return em.createQuery("select br from BookReview br"
                                + " join fetch br.user u"
                                + " join fetch br.book b", BookReview.class)
                .getResultList();

    }

    public void save(BookReview bookReview) {
        em.persist(bookReview);
    }
}
