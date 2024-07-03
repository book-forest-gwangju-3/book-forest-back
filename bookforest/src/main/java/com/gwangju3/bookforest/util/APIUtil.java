package com.gwangju3.bookforest.util;

import com.gwangju3.bookforest.domain.Book;
import org.apache.http.client.utils.URIBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDate;

public class APIUtil {
    private static String urlString = "http://www.aladin.co.kr/ttb/api/ItemList.aspx?SearchTarget=Book&output=js&Version=20131101&InputEncoding=utf-8";

    public static String mapURIString(String queryType, int itemsPerPage, int i, boolean needCategory, String ALADIN_API_KEY) throws URISyntaxException, IOException {
        URIBuilder uriBuilder = new URIBuilder(urlString);
        uriBuilder.addParameter("ttbkey", ALADIN_API_KEY)
                .addParameter("QueryType", queryType)
                .addParameter("MaxResults", String.valueOf(itemsPerPage))
                .addParameter("start", String.valueOf(i));

        if (needCategory) {
            uriBuilder.addParameter("CategoryId", "1");
        }

        URI uri = uriBuilder.build();
        return uri.toString();
    }


    public static Book jsonObjToEntity(JSONObject o, String ALADIN_API_KEY) throws IOException {
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


    public static JSONArray getItemArray(String givenUrl) throws IOException {
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
