package com.gwangju3.bookforest.repository;
import com.gwangju3.bookforest.domain.Book;
import com.gwangju3.bookforest.domain.MyBook;
import com.gwangju3.bookforest.domain.QuickReview;
import com.gwangju3.bookforest.domain.User;
import com.gwangju3.bookforest.domain.like.BookLike;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BookRepository {

    private final EntityManager em;


    public void saveBookLike(BookLike bookLike) {
        em.persist(bookLike);
    }

    public Book findBookById(Long bookId) {
        return em.find(Book.class, bookId);
    }

    public List<Book> searchBook(String q) {
        return em.createQuery("select b from Book b"
                        + " where b.title LIKE :keyword"
                        , Book.class)
                .setParameter("keyword", "%" + q + "%")
                .getResultList();
    }

    public List<Book> findBookByUsername(String username) {
        return em.createQuery(
                "select b from Book b"
                + "where b.username = :username", Book.class)
                .setParameter("username", username)
                .getResultList();
    }


    // 프론트 측에 주는 반환값에 booklike 고유의 id가 존재하지 않음
    public List<BookLike> findBookLikeByUserBook(Long userId, Long bookId) {
        return em.createQuery(
                        "select b from BookLike b"
                                + " where b.user.id = :userId"
                                + " and b.book.id = :bookId"
                        , BookLike.class)
                .setParameter("userId", userId)
                .setParameter("bookId", bookId)
                .getResultList();
    }

    public void deleteBookLike(BookLike bookLike) {
        em.remove(bookLike);
    }

    public List<Book> findBookListByIds(List<Long> ids) {
        return em.createQuery(
                        "select b from Book b"
                                + " where b.id in :ids", Book.class)
                .setParameter("ids", ids)
                .getResultList();
    }
}
