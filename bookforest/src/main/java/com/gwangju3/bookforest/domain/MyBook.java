package com.gwangju3.bookforest.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
public class MyBook {

    @Id
    @GeneratedValue
    @Column(name = "my_book_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    private Integer lastReadPage;

    private Boolean readCompleted;

    protected MyBook() { }

    public MyBook(Integer lastReadPage, boolean readCompleted) {
        this.lastReadPage = lastReadPage;
        this.readCompleted = readCompleted;
    }

    public void setUser(User user) {
        this.user = user;
        getUser().getMyBooks().add(this);
    }
}
