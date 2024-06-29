package com.gwangju3.bookforest.repository;

import com.gwangju3.bookforest.domain.BookReview;
import com.gwangju3.bookforest.domain.User;
import com.gwangju3.bookforest.domain.like.BookReviewLike;
import com.gwangju3.bookforest.domain.like.Like;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class LikeRepository {

    private final EntityManager em;

    public List<BookReviewLike> findBookReviewLikeByBookReviewAndUser(BookReview bookReview, User user) {
        return em.createQuery(
                        "select b from BookReviewLike b where b.user = :user and b.bookReview = :bookReview",
                        BookReviewLike.class)
                .setParameter("user", user)
                .setParameter("bookReview", bookReview)
                .getResultList();
    }

    public void save(Like like) {
        em.persist(like);
    }

    public void delete(Like like) {
        em.remove(like);
    }
}
