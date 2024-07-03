package com.gwangju3.bookforest.repository;

import com.gwangju3.bookforest.domain.MyBook;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MyBookRepository {

    private final EntityManager em;

    public void saveMyBook(MyBook myBook) {
        em.persist(myBook);
    }


    public List<MyBook> findReadingBookListByUserId(Long userId) {
        return em.createQuery(
                        "select m from MyBook m"
                                + " join fetch m.book"
                                + " where m.user.id = :userId"
                                + " and m.readCompleted = false"
                        , MyBook.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    public List<MyBook> findCompletedBookListByUserId(Long userId) {
        return em.createQuery(
                        "select m from MyBook m"
                                + " join fetch m.book"
                                + " where m.user.id = :userId"
                                + " and m.readCompleted = true"
                        , MyBook.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    // id로 고치기
    public List<MyBook> findMyBookByUserBook(String username, Long bookId) {
        if (username == null) return null;
        return em.createQuery(
                        "select m from MyBook m"
                                + " where m.user.username = :username"
                                + " and m.book.id = :bookId"
                        , MyBook.class)
                .setParameter("username", username)
                .setParameter("bookId", bookId)
                .getResultList();
    }
}
