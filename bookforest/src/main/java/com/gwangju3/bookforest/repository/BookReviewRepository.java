package com.gwangju3.bookforest.repository;

import com.gwangju3.bookforest.domain.BookReview;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
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

    public List<BookReview> searchBookReview(String q, String sortBy) {
        String baseQuery = "select br from BookReview br" +
                " join fetch br.user u" +
                " join fetch br.book b" +
                " left join br.comments c" +
                " left join br.bookReviewLikes bl" +
                " where br.title like :q";

        if (!sortBy.isEmpty()) {
            String orderByClause = "";
            if ("likes".equals(sortBy)) {
                orderByClause = " order by count(bl) desc";
            } else if ("recent".equals(sortBy)) {
                orderByClause = " order by br.createdAt desc";
            } else if ("comments".equals(sortBy)) {
                orderByClause = " order by count(c) desc";
            }

            baseQuery += orderByClause;
        }

        TypedQuery<BookReview> query = em.createQuery(baseQuery, BookReview.class);
        query.setParameter("q", "%" + q + "%");

        return query.getResultList();
    }

    public void save(BookReview bookReview) {
        em.persist(bookReview);
    }

    public void delete(BookReview bookReview) {
        em.remove(bookReview);
    }

    public List<BookReview> findBookReviewByUserId(Long userId) {
        return em.createQuery("select br from BookReview br"
                        + " where br.user.id = :userId", BookReview.class)
                .setParameter("userId", userId)
                .getResultList();
    }

}
