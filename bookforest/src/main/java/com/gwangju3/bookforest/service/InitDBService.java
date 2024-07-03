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
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class InitDBService {

    @Value("${ALADIN_API_KEY}")
    private String ALADIN_API_KEY;
    private static String urlString = "http://www.aladin.co.kr/ttb/api/ItemList.aspx?SearchTarget=Book&output=js&Version=20131101&InputEncoding=utf-8";

    private final InitDBRepository initDBRepository;
    private final BookRepository bookRepository;


    public int[] saveBestSeller(InitDBRequest request) throws IOException, URISyntaxException {

        // DB에 저장된 책들의 베스트셀러 순위를 무효처리
        List<Book> allBook = bookRepository.searchBook("");
        for (Book book : allBook) {
            book.setBestRank(null);
        }

        int requestPage = request.getPage();
        int itemsPerPage = request.getItemsPerPage();

        int saveCount = 0;
        int editCount = 0;

        for (int i = 1; i <= requestPage; i++) {
            JSONArray bestSellerJsonArr = mapURIString("Bestseller", itemsPerPage, i, false, ALADIN_API_KEY);

            for (int j = 0; j < bestSellerJsonArr.length(); j++) {
                JSONObject o = bestSellerJsonArr.getJSONObject(j);

                Long id = o.getLong("itemId");
                Integer bestRank = o.getInt("bestRank");

                // DB에 해당 책이 이미 존재한다면 베스트셀러 순위만 바꿔주기
                if (bookRepository.findBookById(id) != null) {
                    Book book = bookRepository.findBookById(id);
                    book.setBestRank(bestRank);
                    editCount++;
                    continue;
                }

                Book book = jsonObjToEntity(o, ALADIN_API_KEY);
                book.setBestRank(bestRank);
                initDBRepository.saveBook(book);
                saveCount++;
            }
        }

        return new int[]{saveCount, editCount};
    }


    public int saveNewAll(InitDBRequest request) throws IOException, URISyntaxException {
        int requestPage = request.getPage();
        int itemsPerPage = request.getItemsPerPage();
        int saveCount = 0;

        for (int i = 1; i <= requestPage; i++) {
            JSONArray newBookJsonArr = mapURIString("ItemNewAll", itemsPerPage, i, false, ALADIN_API_KEY);

            for (int j = 0; j < newBookJsonArr.length(); j++) {
                JSONObject o = newBookJsonArr.getJSONObject(j);

                Long id = o.getLong("itemId");

                if (bookRepository.findBookById(id) != null) {
                    Book book = bookRepository.findBookById(id);
                    continue;
                }

                Book book = jsonObjToEntity(o, ALADIN_API_KEY);
                initDBRepository.saveBook(book);
                saveCount++;
            }
        }

        return saveCount;
    }


    public int saveNewSpecial(InitDBRequest request) throws IOException, URISyntaxException {
        int requestPage = request.getPage();
        int itemsPerPage = request.getItemsPerPage();
        int saveCount = 0;

        for (int i = 1; i <= requestPage; i++) {
            JSONArray newSpecialJsonArr = mapURIString("ItemNewSpecial", itemsPerPage, i, false, ALADIN_API_KEY);

            for (int j = 0; j < newSpecialJsonArr.length(); j++) {
                JSONObject o = newSpecialJsonArr.getJSONObject(j);

                Long id = o.getLong("itemId");

                if (bookRepository.findBookById(id) != null) {
                    Book book = bookRepository.findBookById(id);
                    continue;
                }

                Book book = jsonObjToEntity(o, ALADIN_API_KEY);
                initDBRepository.saveBook(book);

                saveCount++;
            }
        }

        return saveCount;
    }


    public int saveEditorChoice(InitDBRequest request) throws IOException, URISyntaxException {
        int requestPage = request.getPage();
        int itemsPerPage = request.getItemsPerPage();
        int saveCount = 0;

        for (int i = 1; i <= requestPage; i++) {
            JSONArray editorJsonArr = mapURIString("ItemEditorChoice", itemsPerPage, i, true, ALADIN_API_KEY);

            for (int j = 0; j < editorJsonArr.length(); j++) {
                JSONObject o = editorJsonArr.getJSONObject(j);

                Long id = o.getLong("itemId");

                if (bookRepository.findBookById(id) != null) {
                    Book book = bookRepository.findBookById(id);
                    continue;
                }

                Book book = jsonObjToEntity(o, ALADIN_API_KEY);
                initDBRepository.saveBook(book);
                saveCount++;
            }
        }

        return saveCount;
    }


    private static JSONArray mapURIString(String queryType, int itemsPerPage, int i, boolean needCategory, String ALADIN_API_KEY) throws URISyntaxException, IOException {
        URIBuilder uriBuilder = new URIBuilder(urlString);
        uriBuilder.addParameter("ttbkey", ALADIN_API_KEY)
                .addParameter("QueryType", queryType)
                .addParameter("MaxResults", String.valueOf(itemsPerPage))
                .addParameter("start", String.valueOf(i));

        if (needCategory) {
            uriBuilder.addParameter("CategoryId", "1");
        }

        URI uri = uriBuilder.build();
        JSONArray bookArray = getItemArray(uri.toString());
        return bookArray;
    }


    private static Book jsonObjToEntity(JSONObject o, String ALADIN_API_KEY) throws IOException {
        Long id = o.getLong("itemId");
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
        return book;
    }


    private static JSONArray getItemArray(String givenUrl) throws IOException {
        URL url = new URL(givenUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");
        connection.setDoOutput(true);

        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));

        String line = null;
        StringBuilder sb = new StringBuilder();

        while ((line = br.readLine()) != null) {
            sb.append(line);
        }

        JSONObject jsonObject = new JSONObject(sb.toString());
        return jsonObject.getJSONArray("item");
    }
}
