package com.gwangju3.bookforest.controller;

import com.gwangju3.bookforest.domain.Book;
import com.gwangju3.bookforest.dto.InitDBRequest;
import com.gwangju3.bookforest.dto.book.BookDTO;
import com.gwangju3.bookforest.dto.book.ReadBookListResponse;
import com.gwangju3.bookforest.dto.book.UpdateMyBookRequest;
import com.gwangju3.bookforest.mapper.BookMapper;
import com.gwangju3.bookforest.repository.InitDBRepository;
import com.gwangju3.bookforest.service.InitDBService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/initdb")
@RestController
@RequiredArgsConstructor
public class InitDBController {

    private final InitDBService initDBService;

    @PostMapping("/best")
    public ReadBookListResponse saveBestSeller(
            @RequestBody @Valid InitDBRequest request
    ) throws IOException, URISyntaxException {
        List<Book> bestSellerList = initDBService.saveBestSeller(request);
        List<BookDTO> items = bestSellerList.stream()
                .map(BookMapper::entityToDTO)
                .collect(Collectors.toList());
        return new ReadBookListResponse(items);
    }

    @PostMapping("/new-all")
    public ReadBookListResponse saveNewAll(
            @RequestBody @Valid InitDBRequest request
    ) throws IOException, URISyntaxException {
        List<Book> newBookList = initDBService.saveNewAll(request);
        List<BookDTO> items = newBookList.stream()
                .map(BookMapper::entityToDTO)
                .collect(Collectors.toList());
        return new ReadBookListResponse(items);
    }

    @PostMapping("/new-special")
    public ReadBookListResponse saveNewSpecial(
            @RequestBody @Valid InitDBRequest request
    ) throws IOException, URISyntaxException {
        List<Book> newSpecialBookList = initDBService.saveNewSpecial(request);
        List<BookDTO> items = newSpecialBookList.stream()
                .map(BookMapper::entityToDTO)
                .collect(Collectors.toList());
        return new ReadBookListResponse(items);
    }

    @PostMapping("/editor")
    public ReadBookListResponse saveEditorChoice(
            @RequestBody @Valid InitDBRequest request
    ) throws IOException, URISyntaxException {
        List<Book> editorChoiceList = initDBService.saveEditorChoice(request);
        List<BookDTO> items = editorChoiceList.stream()
                .map(BookMapper::entityToDTO)
                .collect(Collectors.toList());
        return new ReadBookListResponse(items);
    }
}
