package com.gwangju3.bookforest.repository;
import com.gwangju3.bookforest.domain.Book;
import com.gwangju3.bookforest.domain.MyBook;
import com.gwangju3.bookforest.domain.QuickReview;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BookRepository {

    private final EntityManager em;

    public void saveMyBook(MyBook myBook) {
        em.persist(myBook);
    }

    public void saveQuickReview(QuickReview quickReview) {
        em.persist(quickReview);
    }

    public Book findBookById(Long bookId) {
        return em.find(Book.class, bookId);
    }

    public QuickReview findQuickReviewById(Long quickReviewId) {
        return em.find(QuickReview.class, quickReviewId);
    }

    public List<Book> findAllBook() {
        return em.createQuery("select b from Book b", Book.class)
                .getResultList();
    }

    public List<Book> findBookByUsername(String username) {
        return em.createQuery(
                "select b from Book b"
                + "where b.username = :username", Book.class)
                .setParameter("username", username)
                .getResultList();
    }

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

    public void deleteQuickReview(QuickReview quickReview) {
        em.remove(quickReview);
    }
}
