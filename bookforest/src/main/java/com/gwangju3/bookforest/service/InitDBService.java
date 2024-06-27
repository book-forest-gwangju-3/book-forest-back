package com.gwangju3.bookforest.service;

import com.gwangju3.bookforest.domain.Book;
import com.gwangju3.bookforest.dto.InitDBRequest;
import com.gwangju3.bookforest.repository.BookRepository;
import com.gwangju3.bookforest.repository.InitDBRepository;
import lombok.RequiredArgsConstructor;
import org.apache.http.client.utils.URIBuilder;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class InitDBService {
    @Value("${ALADIN_API_KEY}")
    private String ALADIN_API_KEY;
    private String urlString = "http://www.aladin.co.kr/ttb/api/ItemList.aspx?SearchTarget=Book&output=js&Version=20131101";

    private final InitDBRepository initDBRepository;
    private final BookRepository bookRepository;


    public List<Book> saveBestSeller(InitDBRequest request) throws IOException, URISyntaxException {

        // DB에 저장된 책들의 베스트셀러 순위를 무효처리
        List<Book> allBook = bookRepository.findAllBook();
        for (Book book : allBook) {
            book.setBestRank(null);
        }

        int requestPage = request.getPage();
        int itemsPerPage = request.getItemsPerPage();
        List<Book> bestSellerList = new ArrayList<>();

        for (int i = 1; i <= requestPage; i++) {
            URIBuilder uriBuilder = new URIBuilder(urlString);
            uriBuilder.addParameter("ttbkey", ALADIN_API_KEY)
                    .addParameter("QueryType", "Bestseller")
                    .addParameter("MaxResults", String.valueOf(itemsPerPage))
                    .addParameter("start", String.valueOf(i));

            URI uri = uriBuilder.build();
            JSONArray bestSellerArray = getItemArray(uri.toString());

            for (int j = 0; j < bestSellerArray.length(); j++) {
                JSONObject o = bestSellerArray.getJSONObject(j);

                Long id = o.getLong("itemId");
                Integer bestRank = o.getInt("bestRank");

                // DB에 해당 책이 이미 존재한다면 베스트셀러 순위만 바꿔주기
                if (bookRepository.findBookById(id) != null) {
                    Book book = bookRepository.findBookById(id);
                    book.setBestRank(bestRank);
                    if (bestSellerList.size() < 50) {
                        bestSellerList.add(book);
                    }
                    continue;
                }

                String title = o.getString("title");
                String author = o.getString("author").split(" \\(")[0];
                LocalDate pubDate = LocalDate.parse(o.getString("pubDate"));
                String description = o.getString("description");
                String coverUrl = o.getString("cover");
                Integer standardPrice = o.getInt("priceStandard");
                String publisher = o.getString("publisher");
                String categoryName = o.getString("categoryName");

                String newUrl = "http://www.aladin.co.kr/ttb/api/ItemLookUp.aspx?ttbkey=" + ALADIN_API_KEY + "&itemIdType=ItemId&ItemId=" + id + "&output=js&Version=20131101";
                JSONObject detailObj = getItemArray(newUrl).getJSONObject(0);
                JSONObject subInfo = detailObj.getJSONObject("subInfo");
                Integer page = subInfo.getInt("itemPage");
                Book book = new Book(id, title, author, pubDate, description, coverUrl, bestRank, page, standardPrice, publisher, categoryName);

                initDBRepository.saveBook(book);

                if (bestSellerList.size() < 50) {
                    bestSellerList.add(book);
                }
            }
        }

        return bestSellerList;
    }


    public List<Book> saveNewAll(InitDBRequest request) throws IOException, URISyntaxException {
        int requestPage = request.getPage();
        int itemsPerPage = request.getItemsPerPage();
        List<Book> newBookList = new ArrayList<>();

        for (int i = 1; i <= requestPage; i++) {
            URIBuilder uriBuilder = new URIBuilder(urlString);
            uriBuilder.addParameter("ttbkey", ALADIN_API_KEY)
                    .addParameter("QueryType", "ItemNewAll")
                    .addParameter("MaxResults", String.valueOf(itemsPerPage))
                    .addParameter("start", String.valueOf(i));

            URI uri = uriBuilder.build();
            JSONArray bestSellerArray = getItemArray(uri.toString());

            for (int j = 0; j < bestSellerArray.length(); j++) {
                JSONObject o = bestSellerArray.getJSONObject(j);

                Long id = o.getLong("itemId");

                if (bookRepository.findBookById(id) != null) {
                    Book book = bookRepository.findBookById(id);
                    if (newBookList.size() < 50) {
                        newBookList.add(book);
                    }
                    continue;
                }

                String title = o.getString("title");
                String author = o.getString("author").split(" \\(")[0];
                LocalDate pubDate = LocalDate.parse(o.getString("pubDate"));
                String description = o.getString("description");
                String coverUrl = o.getString("cover");
                Integer standardPrice = o.getInt("priceStandard");
                String publisher = o.getString("publisher");
                String categoryName = o.getString("categoryName");

                String newUrl = "http://www.aladin.co.kr/ttb/api/ItemLookUp.aspx?ttbkey=" + ALADIN_API_KEY + "&itemIdType=ItemId&ItemId=" + id + "&output=js&Version=20131101";
                JSONObject detailObj = getItemArray(newUrl).getJSONObject(0);
                JSONObject subInfo = detailObj.getJSONObject("subInfo");
                Integer page = subInfo.getInt("itemPage");
                Book book = new Book(id, title, author, pubDate, description, coverUrl, page, standardPrice, publisher, categoryName);

                initDBRepository.saveBook(book);

                if (newBookList.size() < 50) {
                    newBookList.add(book);
                }
            }
        }

        return newBookList;
    }


    public List<Book> saveNewSpecial(InitDBRequest request) throws IOException, URISyntaxException {
        int requestPage = request.getPage();
        int itemsPerPage = request.getItemsPerPage();
        List<Book> newBookList = new ArrayList<>();

        for (int i = 1; i <= requestPage; i++) {
            URIBuilder uriBuilder = new URIBuilder(urlString);
            uriBuilder.addParameter("ttbkey", ALADIN_API_KEY)
                    .addParameter("QueryType", "ItemNewSpecial")
                    .addParameter("MaxResults", String.valueOf(itemsPerPage))
                    .addParameter("start", String.valueOf(i));

            URI uri = uriBuilder.build();
            JSONArray bestSellerArray = getItemArray(uri.toString());

            for (int j = 0; j < bestSellerArray.length(); j++) {
                JSONObject o = bestSellerArray.getJSONObject(j);

                Long id = o.getLong("itemId");

                if (bookRepository.findBookById(id) != null) {
                    Book book = bookRepository.findBookById(id);
                    if (newBookList.size() < 50) {
                        newBookList.add(book);
                    }
                    continue;
                }

                String title = o.getString("title");
                String author = o.getString("author").split(" \\(")[0];
                LocalDate pubDate = LocalDate.parse(o.getString("pubDate"));
                String description = o.getString("description");
                String coverUrl = o.getString("cover");
                Integer standardPrice = o.getInt("priceStandard");
                String publisher = o.getString("publisher");
                String categoryName = o.getString("categoryName");

                String newUrl = "http://www.aladin.co.kr/ttb/api/ItemLookUp.aspx?ttbkey=" + ALADIN_API_KEY + "&itemIdType=ItemId&ItemId=" + id + "&output=js&Version=20131101";
                JSONObject detailObj = getItemArray(newUrl).getJSONObject(0);
                JSONObject subInfo = detailObj.getJSONObject("subInfo");
                Integer page = subInfo.getInt("itemPage");
                Book book = new Book(id, title, author, pubDate, description, coverUrl, page, standardPrice, publisher, categoryName);

                initDBRepository.saveBook(book);

                if (newBookList.size() < 50) {
                    newBookList.add(book);
                }
            }
        }

        return newBookList;
    }


    public List<Book> saveEditorChoice(InitDBRequest request) throws IOException, URISyntaxException {
        int requestPage = request.getPage();
        int itemsPerPage = request.getItemsPerPage();
        List<Book> newBookList = new ArrayList<>();

        for (int i = 1; i <= requestPage; i++) {
            URIBuilder uriBuilder = new URIBuilder(urlString);
            uriBuilder.addParameter("ttbkey", ALADIN_API_KEY)
                    .addParameter("QueryType", "ItemEditorChoice")
                    .addParameter("CategoryId", "1")
                    .addParameter("MaxResults", String.valueOf(itemsPerPage))
                    .addParameter("start", String.valueOf(i));

            URI uri = uriBuilder.build();
            JSONArray bestSellerArray = getItemArray(uri.toString());

            for (int j = 0; j < bestSellerArray.length(); j++) {
                JSONObject o = bestSellerArray.getJSONObject(j);

                Long id = o.getLong("itemId");

                if (bookRepository.findBookById(id) != null) {
                    Book book = bookRepository.findBookById(id);
                    if (newBookList.size() < 50) {
                        newBookList.add(book);
                    }
                    continue;
                }

                String title = o.getString("title");
                String author = o.getString("author").split(" \\(")[0];
                LocalDate pubDate = LocalDate.parse(o.getString("pubDate"));
                String description = o.getString("description");
                String coverUrl = o.getString("cover");
                Integer standardPrice = o.getInt("priceStandard");
                String publisher = o.getString("publisher");
                String categoryName = o.getString("categoryName");

                String newUrl = "http://www.aladin.co.kr/ttb/api/ItemLookUp.aspx?ttbkey=" + ALADIN_API_KEY + "&itemIdType=ItemId&ItemId=" + id + "&output=js&Version=20131101";
                JSONObject detailObj = getItemArray(newUrl).getJSONObject(0);
                JSONObject subInfo = detailObj.getJSONObject("subInfo");
                Integer page = subInfo.getInt("itemPage");
                Book book = new Book(id, title, author, pubDate, description, coverUrl, page, standardPrice, publisher, categoryName);

                initDBRepository.saveBook(book);

                if (newBookList.size() < 50) {
                    newBookList.add(book);
                }
            }
        }

        return newBookList;
    }


    private static JSONArray getItemArray(String givenUrl) throws IOException {
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
