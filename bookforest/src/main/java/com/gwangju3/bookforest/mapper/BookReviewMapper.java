package com.gwangju3.bookforest.mapper;

import com.gwangju3.bookforest.domain.Book;
import com.gwangju3.bookforest.domain.BookReview;
import com.gwangju3.bookforest.domain.Comment;
import com.gwangju3.bookforest.domain.User;
import com.gwangju3.bookforest.domain.like.BookReviewLike;
import com.gwangju3.bookforest.dto.bookreview.BookReviewBookDTO;
import com.gwangju3.bookforest.dto.bookreview.BookReviewDTO;
import com.gwangju3.bookforest.dto.bookreview.BookReviewDetailDTO;
import com.gwangju3.bookforest.dto.comment.CommentDTO;
import com.gwangju3.bookforest.dto.user.UserDTO;
import com.gwangju3.bookforest.dto.user.UserRankingDTO;

import java.util.List;

public class BookReviewMapper {
    public static BookReviewDTO toDTO(BookReview bookReview, String currentUsername) {
        User user = bookReview.getUser();
        Book book = bookReview.getBook();
        List<BookReviewLike> likes = bookReview.getBookReviewLikes();

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


        boolean isLikedByCurrentUser = likes.stream()
                .anyMatch(like -> like.getUser().getUsername().equals(currentUsername));

        return new BookReviewDTO(
                bookReview.getId(),
                bookReview.getTitle(),
                bookReview.getContent(),
                bookReview.getCreatedAt(),
                bookReview.getUpdatedAt(),
                userDTO,
                bookReviewBookDTO,
                (long) likes.size(),
                isLikedByCurrentUser
        );
    }

    public static BookReviewDetailDTO toDetailDTO(BookReview bookReview, String currentUsername) {
        User user = bookReview.getUser();
        Book book = bookReview.getBook();
        List<Comment> comments = bookReview.getComments();
        List<BookReviewLike> likes = bookReview.getBookReviewLikes();

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

        boolean isLikedByCurrentUser = likes.stream()
                .anyMatch(like -> like.getUser().getUsername().equals(currentUsername));


        return new BookReviewDetailDTO(
                bookReview.getId(),
                bookReview.getTitle(),
                bookReview.getContent(),
                bookReview.getCreatedAt(),
                bookReview.getUpdatedAt(),
                userDTO,
                bookReviewBookDTO,
                commentDTOs,
                (long) likes.size(),
                isLikedByCurrentUser
        );
    }
}
