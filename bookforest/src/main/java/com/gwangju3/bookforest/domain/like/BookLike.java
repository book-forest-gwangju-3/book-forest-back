package com.gwangju3.bookforest.domain.like;


import com.gwangju3.bookforest.domain.Book;
import com.gwangju3.bookforest.domain.User;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@DiscriminatorValue("BOOK")
public class BookLike extends Like{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    public void setBook(Book book) {
        this.book = book;
        getBook().getBookLikes().add(this);
    }

    @Override
    public void setUser(User user) {
        super.setUser(user);
        user.getBookLikes().add(this);
    }
}
