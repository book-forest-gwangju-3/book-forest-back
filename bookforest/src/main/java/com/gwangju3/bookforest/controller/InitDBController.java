package com.gwangju3.bookforest.controller;

import com.gwangju3.bookforest.domain.Book;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class InitDBController {

    private final EntityManager em;

    @GetMapping("/initdb")
    public String initDB() throws IOException {
        JSONArray bestSellerArray = parseJson("http://www.aladin.co.kr/ttb/api/ItemList.aspx?ttbkey=ttbchakkerly1400002&QueryType=Bestseller&MaxResults=10&start=1&SearchTarget=Book&output=js&Version=20131101");
        for (int i = 0; i < 1; i++) {
            JSONObject o = bestSellerArray.getJSONObject(i);
            Long id = o.getLong("itemId");
            System.out.println(id);
            String title = o.getString("title");
            String author = o.getString("author").split(" \\(")[0];

            LocalDate pubDate = LocalDate.parse(o.getString("pubDate"));
            String description = o.getString("description");
            String coverUrl = o.getString("cover");
            Integer bestRank = o.getInt("bestRank");

            String newUrl = "http://www.aladin.co.kr/ttb/api/ItemLookUp.aspx?ttbkey=ttbchakkerly1400002&itemIdType=ItemId&ItemId=" + id + "&output=js&Version=20131101";
            JSONObject detailObj = parseJson(newUrl).getJSONObject(0);
            JSONObject subInfo = detailObj.getJSONObject("subInfo");
            Integer page = subInfo.getInt("itemPage");
            Book book = new Book(id, title, author, pubDate, description, coverUrl, bestRank, page);
            System.out.println(book);
        }

        return "success";
    }

    private static JSONArray parseJson(String givenUrl) throws IOException {
        URL url = new URL(givenUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");
        connection.setDoOutput(true);

        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        String line = null;
        StringBuilder sb = new StringBuilder();

        while ((line = br.readLine()) != null) {
            sb.append(line);
        }

        JSONObject jsonObject = new JSONObject(sb.toString());
        return jsonObject.getJSONArray("item");
    }

}
