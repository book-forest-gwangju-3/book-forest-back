package com.gwangju3.bookforest.domain.commit;

import com.gwangju3.bookforest.domain.MyBook;
import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Entity
@Getter
@DiscriminatorValue("READ")
public class ReadCommit extends Commit {
    private Integer readPages;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "my_book_id")
    private MyBook myBook;

    public ReadCommit(Integer readPages, MyBook myBook) {
        super(readPages);
        this.readPages = readPages;
        this.myBook = myBook;
    }

    protected ReadCommit() {

    }
}
