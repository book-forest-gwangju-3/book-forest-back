package com.gwangju3.bookforest.mapper;

import com.gwangju3.bookforest.domain.Book;
import com.gwangju3.bookforest.domain.BookReview;
import com.gwangju3.bookforest.domain.Comment;
import com.gwangju3.bookforest.domain.User;
import com.gwangju3.bookforest.dto.bookreview.BookReviewBookDTO;
import com.gwangju3.bookforest.dto.bookreview.BookReviewDTO;
import com.gwangju3.bookforest.dto.bookreview.BookReviewDetailDTO;
import com.gwangju3.bookforest.dto.comment.CommentDTO;
import com.gwangju3.bookforest.dto.user.UserDTO;

import java.util.List;

public class BookReviewMapper {
    public static BookReviewDTO toDTO(BookReview bookReview) {
        User user = bookReview.getUser();
        Book book = bookReview.getBook();

        UserDTO userDTO = new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getNickname()
        );

        BookReviewBookDTO bookReviewBookDTO = new BookReviewBookDTO(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getCoverUrl()
        );

        return new BookReviewDTO(
                bookReview.getId(),
                bookReview.getTitle(),
                bookReview.getContent(),
                bookReview.getCreatedAt(),
                bookReview.getUpdatedAt(),
                userDTO,
                bookReviewBookDTO
        );
    }

    public static BookReviewDetailDTO toDetailDTO(BookReview bookReview) {
        User user = bookReview.getUser();
        Book book = bookReview.getBook();
        List<Comment> comments = bookReview.getComments();

        UserDTO userDTO = new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getNickname()
        );

        BookReviewBookDTO bookReviewBookDTO = new BookReviewBookDTO(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getCoverUrl()
        );

        List<CommentDTO> commentDTOs =  comments.stream()
                .map(CommentMapper::toDTO)
                .toList();

        return new BookReviewDetailDTO(
                bookReview.getId(),
                bookReview.getTitle(),
                bookReview.getContent(),
                bookReview.getCreatedAt(),
                bookReview.getUpdatedAt(),
                userDTO,
                bookReviewBookDTO,
                commentDTOs
        );
    }
}
