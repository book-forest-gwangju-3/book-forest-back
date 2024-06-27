package com.gwangju3.bookforest.repository;

import com.gwangju3.bookforest.domain.Book;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

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

    public String initDB(int numOfPages) throws IOException {
        for (int i = 1; i <= numOfPages; i++) {
            String url = "http://www.aladin.co.kr/ttb/api/ItemList.aspx?ttbkey=" + ALADIN_API_KEY + "&QueryType=Bestseller&MaxResults=50&start="+ i + "&SearchTarget=Book&output=js&Version=20131101";
            JSONArray bestSellerArray = parseJson(url);
            for (int j = 0; j < bestSellerArray.length(); j++) {
                JSONObject o = bestSellerArray.getJSONObject(j);
                Long id = o.getLong("itemId");
                if (bookRepository.findBookById(id) != null) continue;
                String title = o.getString("title");
                String author = o.getString("author").split(" \\(")[0];

                LocalDate pubDate = LocalDate.parse(o.getString("pubDate"));
                String description = o.getString("description");
                String coverUrl = o.getString("cover");
                Integer bestRank = o.getInt("bestRank");
                Integer standardPrice = o.getInt("priceStandard");
                String publisher = o.getString("publisher");
                String categoryName = o.getString("categoryName");

                String newUrl = "http://www.aladin.co.kr/ttb/api/ItemLookUp.aspx?ttbkey=ttbchakkerly1400002&itemIdType=ItemId&ItemId=" + id + "&output=js&Version=20131101";
                JSONObject detailObj = parseJson(newUrl).getJSONObject(0);
                JSONObject subInfo = detailObj.getJSONObject("subInfo");
                Integer page = subInfo.getInt("itemPage");
                Book book = new Book(id, title, author, pubDate, description, coverUrl, bestRank, page, standardPrice, publisher, categoryName);
                em.persist(book);
            }
        }

        return numOfPages + " page(s) saved";
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
