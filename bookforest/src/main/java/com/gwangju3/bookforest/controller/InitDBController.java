package com.gwangju3.bookforest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@RestController
@RequiredArgsConstructor
public class InitDBController {

    @GetMapping("/initdb")
    public String initDB() throws IOException {
        URL url = new URL("http://www.aladin.co.kr/ttb/api/ItemList.aspx?ttbkey=ttbchakkerly1400002&QueryType=Bestseller&MaxResults=10&start=1&SearchTarget=Book&output=js&Version=20131101");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");
        connection.setDoOutput(true);

        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        String line = null;

        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }

        return "success";
    }

}
