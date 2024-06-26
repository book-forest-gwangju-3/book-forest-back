package com.gwangju3.bookforest.controller;

import com.gwangju3.bookforest.domain.Book;
import com.gwangju3.bookforest.repository.InitDBRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class InitDBController {

    private final InitDBRepository initDBRepository;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/initdb/{page}")
    public String initDB(@PathVariable("page") String page) throws IOException {
        // 1페이지당 책 50개 저장
        // 최대 20페이지까지 가능 (최대 1000개)
        return initDBRepository.initDB(Integer.parseInt(page));
    }
}
