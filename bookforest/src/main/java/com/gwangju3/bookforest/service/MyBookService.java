package com.gwangju3.bookforest.service;

import com.gwangju3.bookforest.domain.Book;
import com.gwangju3.bookforest.domain.MyBook;
import com.gwangju3.bookforest.domain.User;
import com.gwangju3.bookforest.exception.book.InvalidPageException;
import com.gwangju3.bookforest.repository.MyBookRepository;
import com.gwangju3.bookforest.repository.UserRepository;
import com.gwangju3.bookforest.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MyBookService {

    private final MyBookRepository myBookRepository;
    private final UserRepository userRepository;
    private final CommitService commitService;
    private final BookService bookRepository;

    public MyBook findMyBookByUserBook(Long bookId) {
        List<MyBook> myBookList = myBookRepository.findMyBookByUserBook(UserUtil.extractUsername(), bookId);
        // 비로그인 상태 || 유저가 독서한 적 없는 책일 때
        if (myBookList == null || (myBookList.isEmpty())) {
            return null;
        } else {
            return myBookList.get(0);
        }
    }

    public List<MyBook> findReadingBookListByUserId (Long userId) {
        return myBookRepository.findReadingBookListByUserId(userId);
    }

    public List<MyBook> findCompletedBookListByUserId (Long userId) {
        return myBookRepository.findCompletedBookListByUserId(userId);
    }

    @Transactional
    public MyBook createMyBook(Long bookId) {
        String username = UserUtil.extractUsername();
        User user = userRepository.findByUsername(username).get(0);

        Book book = bookRepository.findBookById(bookId);
        MyBook myBook = new MyBook(0, false);
        myBook.setBook(book);

        myBook.setUser(user);
        myBook.setBook(book);

        myBookRepository.saveMyBook(myBook);
        return myBook;
    }

    @Transactional
    public MyBook updateMyBook(long bookId, Integer page) {
        MyBook mybook = myBookRepository.findMyBookByUserBook(UserUtil.extractUsername(), bookId).get(0);
        Integer readPage = page - mybook.getLastReadPage();
        boolean didUpdate = mybook.setLastReadPage(page);
        if (didUpdate) {
            commitService.createReadCommit(readPage, mybook);
            return mybook;
        } else {
            throw new InvalidPageException();
        }
    }
}
