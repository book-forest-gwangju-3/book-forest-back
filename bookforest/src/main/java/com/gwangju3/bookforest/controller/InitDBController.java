package com.gwangju3.bookforest.controller;

import com.gwangju3.bookforest.dto.AladinAPIRequest;
import com.gwangju3.bookforest.dto.MessageResponse;
import com.gwangju3.bookforest.service.InitDBService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;

@RequestMapping("/initdb")
@RestController
@RequiredArgsConstructor
public class InitDBController {

    private final InitDBService initDBService;

    @PostMapping("/bomb")
    public ResponseEntity<MessageResponse> oneClickSave(
            @RequestBody @Valid AladinAPIRequest request
    ) throws IOException, URISyntaxException {

        int newAllCount = initDBService.saveNewAll(request);
        int newSpecialCount = initDBService.saveNewSpecial(request);
        int editorChoiceCount = initDBService.saveEditorChoice(request);
        int[] bestCounts = initDBService.saveBestSeller(request);

        int saveCount = newAllCount + newSpecialCount + editorChoiceCount + bestCounts[0];
        int editCount = bestCounts[1];

        MessageResponse messageResponse = new MessageResponse(String.format("%d권의 책을 저장하고 %d권의 베스트셀러 순위를 갱신하였습니다.", saveCount, editCount));

        if (saveCount > 0) {
            return new ResponseEntity<>(messageResponse, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(messageResponse, HttpStatus.OK);
        }
    }


    @PostMapping("/best")
    public ResponseEntity<MessageResponse> saveBestSeller(
            @RequestBody @Valid AladinAPIRequest request
    ) throws IOException, URISyntaxException {
        int[] counts = initDBService.saveBestSeller(request);
        MessageResponse messageResponse = new MessageResponse(String.format("%d권의 베스트셀러를 저장하고 %d권의 베스트셀러 순위를 갱신하였습니다.", counts[0], counts[1]));
        if (counts[0] > 0) {
            return new ResponseEntity<>(messageResponse, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(messageResponse, HttpStatus.OK);
        }
    }

    @PostMapping("/new-all")
    public ResponseEntity<MessageResponse> saveNewAll(
            @RequestBody @Valid AladinAPIRequest request
    ) throws IOException, URISyntaxException {
        int saveCount = initDBService.saveNewAll(request);
        MessageResponse messageResponse = new MessageResponse(String.format("%d권의 신간을 저장하였습니다.", saveCount));
        if (saveCount > 0) {
            return new ResponseEntity<>(messageResponse, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(messageResponse, HttpStatus.OK);
        }
    }

    @PostMapping("/new-special")
    public ResponseEntity<MessageResponse> saveNewSpecial(
            @RequestBody @Valid AladinAPIRequest request
    ) throws IOException, URISyntaxException {
        int saveCount = initDBService.saveNewSpecial(request);
        MessageResponse messageResponse = new MessageResponse(String.format("%d권의 주목할만한 신간을 저장하였습니다.", saveCount));
        if (saveCount > 0) {
            return new ResponseEntity<>(messageResponse, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(messageResponse, HttpStatus.OK);
        }
    }

    @PostMapping("/editor")
    public ResponseEntity<MessageResponse> saveEditorChoice(
            @RequestBody @Valid AladinAPIRequest request
    ) throws IOException, URISyntaxException {
        int saveCount = initDBService.saveEditorChoice(request);
        MessageResponse messageResponse = new MessageResponse(String.format("%d권의 편집자 추천 책을 저장하였습니다.", saveCount));
        if (saveCount > 0) {
            return new ResponseEntity<>(messageResponse, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(messageResponse, HttpStatus.OK);
        }
    }
}
