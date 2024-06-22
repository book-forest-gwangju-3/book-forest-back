package com.gwangju3.bookforest.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@AllArgsConstructor
public class Book {

    @Id
    @Column(name = "book_id")
    private Long id;

    private String title;

    private String author;

    private LocalDate pubdate;

    private String description;

    private String coverUrl;

    private Integer bestRank;

    private Integer page;

    protected Book() {

    }

    // 나중에 꼭 지워야 됨
    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", pubdate=" + pubdate +
                ", description='" + description + '\'' +
                ", coverUrl='" + coverUrl + '\'' +
                ", bestRank=" + bestRank +
                ", page=" + page +
                '}';
    }
}
