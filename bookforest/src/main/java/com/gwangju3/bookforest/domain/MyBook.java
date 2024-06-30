package com.gwangju3.bookforest.domain;

import com.gwangju3.bookforest.domain.commit.ReadCommit;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "myBook", cascade = CascadeType.ALL)
    private List<ReadCommit> readCommit = new ArrayList<>();

    protected MyBook() { }

    public MyBook(Integer lastReadPage, boolean readCompleted) {
        this.lastReadPage = lastReadPage;
        this.readCompleted = readCompleted;
    }

    public void setUser(User user) {
        this.user = user;
        getUser().getMyBooks().add(this);
    }

    public boolean setLastReadPage(Integer page) {
        if (page > getBook().getPage() || page <= getLastReadPage()) {
            return false;
        } else {
            if (page.equals(getBook().getPage())) {
                this.readCompleted = true;
            }
            this.lastReadPage = page;
            return true;
        }
    }
}
