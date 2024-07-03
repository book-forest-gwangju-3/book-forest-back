package com.gwangju3.bookforest.service;

import com.gwangju3.bookforest.domain.Book;
import com.gwangju3.bookforest.domain.User;
import com.gwangju3.bookforest.domain.like.BookLike;
import com.gwangju3.bookforest.dto.AladinAPIRequest;
import com.gwangju3.bookforest.dto.book.CreateBookLikeRequest;
import com.gwangju3.bookforest.repository.BookRepository;
import com.gwangju3.bookforest.repository.UserRepository;
import com.gwangju3.bookforest.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static com.gwangju3.bookforest.util.APIUtil.*;


@Service
@Transactional
@RequiredArgsConstructor
public class BookService {

    @Value("${ALADIN_API_KEY}")
    private String ALADIN_API_KEY;

    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public Book findBookById(Long bookId) {
        return bookRepository.findBookById(bookId);
    }

    @Transactional(readOnly = true)
    public List<Book> searchBook(String q) {
        return bookRepository.searchBook(q);
    }

    public boolean toggleBookLike(CreateBookLikeRequest request) {
        User user = userRepository.findByUsername(UserUtil.extractUsername()).get(0);
        Book book = bookRepository.findBookById(request.getBookId());

        List<BookLike> bookLikes = bookRepository.findBookLikeByUserBook(user.getId(), book.getId());

        if (bookLikes.isEmpty()) {
            BookLike bookLike = new BookLike();

            bookLike.setBook(book);
            bookLike.setUser(user);

            bookRepository.saveBookLike(bookLike);

            return true;
        } else {
            BookLike bookLike = bookLikes.get(0);
            bookRepository.deleteBookLike(bookLike);

            return false;
        }
    }

    public List<Book> findBestSeller(AladinAPIRequest request) throws URISyntaxException, IOException {
        List<Long> ids = findIds(request, "BestSeller", false, ALADIN_API_KEY);

        List<Book> bookList = bookRepository.findBookListByIds(ids);
        return bookList;
    }

    public List<Book> findNewAll(AladinAPIRequest request) throws URISyntaxException, IOException {
        List<Long> ids = findIds(request, "ItemNewAll", false, ALADIN_API_KEY);

        List<Book> bookList = bookRepository.findBookListByIds(ids);
        return bookList;
    }

    public List<Book> findNewSpecial(AladinAPIRequest request) throws URISyntaxException, IOException {
        List<Long> ids = findIds(request, "ItemNewSpecial", false, ALADIN_API_KEY);

        List<Book> bookList = bookRepository.findBookListByIds(ids);
        return bookList;
    }

    public List<Book> findEditorChoice(AladinAPIRequest request) throws URISyntaxException, IOException {
        List<Long> ids = findIds(request, "ItemEditorChoice", true, ALADIN_API_KEY);

        List<Book> bookList = bookRepository.findBookListByIds(ids);
        return bookList;
    }

    private List<Long> findIds(AladinAPIRequest request, String queryType, boolean needCategory, String ALADIN_API_KEY) throws IOException, URISyntaxException {
        int requestPage = request.getPage();
        int itemsPerPage = request.getItemsPerPage();

        List<Long> ids = new ArrayList<>();

        for (int i = 1; i <= requestPage; i++) {
            String url = mapURIString(queryType, itemsPerPage, i, needCategory, ALADIN_API_KEY);
            JSONArray jsonArray = getItemArray(url);
            for (int j = 0; j < jsonArray.length(); j++) {
                JSONObject o = jsonArray.getJSONObject(j);
                ids.add(o.getLong("itemId"));
            }
        }

        return ids;
    }
}
