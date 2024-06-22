package com.gwangju3.bookforest.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

import java.time.LocalDate;

@Entity
@Getter
public class Book {

    @Id @GeneratedValue
    @Column(name = "book_id")
    private Long id;

    private String title;

    private String author;

    private LocalDate pubdate;

    private String description;

    private String coverUrl;

    private Integer bestRank;

    private Integer page;
}
