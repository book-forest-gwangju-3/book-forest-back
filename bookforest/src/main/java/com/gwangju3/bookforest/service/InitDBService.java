package com.gwangju3.bookforest.service;

import com.gwangju3.bookforest.domain.Book;
import com.gwangju3.bookforest.dto.AladinAPIRequest;
import com.gwangju3.bookforest.repository.BookRepository;
import com.gwangju3.bookforest.repository.InitDBRepository;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static com.gwangju3.bookforest.util.APIUtil.jsonObjToEntity;
import static com.gwangju3.bookforest.util.APIUtil.mapURIString;
import static com.gwangju3.bookforest.util.APIUtil.getItemArray;

@Service
@Transactional
@RequiredArgsConstructor
public class InitDBService {

    @Value("${ALADIN_API_KEY}")
    private String ALADIN_API_KEY;

    private final InitDBRepository initDBRepository;
    private final BookRepository bookRepository;


    public int[] saveBestSeller(AladinAPIRequest request) throws IOException, URISyntaxException {

        // DB에 저장된 모든 책들의 베스트셀러 순위를 무효처리 (새로운 순위를 받아와야 하므로)
        bookRepository.clearBestRank();

        int requestPage = request.getPage();
        int itemsPerPage = request.getItemsPerPage();

        int saveCount = 0;
        int editCount = 0;

        for (int i = 1; i <= requestPage; i++) {
            String url = mapURIString("Bestseller", itemsPerPage, i, false, ALADIN_API_KEY);
            JSONArray bestSellerJsonArr = getItemArray(url);

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


    public int saveNewAll(AladinAPIRequest request) throws IOException, URISyntaxException {
        int requestPage = request.getPage();
        int itemsPerPage = request.getItemsPerPage();
        int saveCount = 0;

        for (int i = 1; i <= requestPage; i++) {
            String url = mapURIString("ItemNewAll", itemsPerPage, i, false, ALADIN_API_KEY);
            JSONArray newBookJsonArr = getItemArray(url);

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


    public int saveNewSpecial(AladinAPIRequest request) throws IOException, URISyntaxException {
        int requestPage = request.getPage();
        int itemsPerPage = request.getItemsPerPage();
        int saveCount = 0;

        for (int i = 1; i <= requestPage; i++) {
            String url = mapURIString("ItemNewSpecial", itemsPerPage, i, false, ALADIN_API_KEY);
            JSONArray newSpecialJsonArr = getItemArray(url);

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


    public int saveEditorChoice(AladinAPIRequest request) throws IOException, URISyntaxException {
        int requestPage = request.getPage();
        int itemsPerPage = request.getItemsPerPage();
        int saveCount = 0;

        for (int i = 1; i <= requestPage; i++) {
            String url = mapURIString("ItemEditorChoice", itemsPerPage, i, true, ALADIN_API_KEY);
            JSONArray editorJsonArr = getItemArray(url);

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
}
