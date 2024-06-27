package com.gwangju3.bookforest.repository;

import com.gwangju3.bookforest.domain.Book;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;

@Transactional
@Repository
@RequiredArgsConstructor
public class InitDBRepository {
    @Value("${ALADIN_API_KEY}")
    private String ALADIN_API_KEY;

    private final EntityManager em;
    private final BookRepository bookRepository;

    public void saveBook(Book book) {

        em.persist(book);
    }
}
