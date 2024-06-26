package com.gwangju3.bookforest.repository;
import com.gwangju3.bookforest.domain.Book;
import com.gwangju3.bookforest.domain.MyBook;
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

    public Book findBookById(Long bookId) {
        return em.find(Book.class, bookId);
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
        return em.createQuery(
                        "select m from MyBook m"
                                + " where m.username = :username"
                                + " and m.book.id = :bookId"
                        , MyBook.class)
                .setParameter("username", username)
                .setParameter("bookId", bookId)
                .getResultList();
    }
}
