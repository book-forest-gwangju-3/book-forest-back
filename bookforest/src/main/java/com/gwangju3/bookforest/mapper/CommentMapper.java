package com.gwangju3.bookforest.mapper;

import com.gwangju3.bookforest.domain.Comment;
import com.gwangju3.bookforest.domain.User;
import com.gwangju3.bookforest.dto.comment.CommentDTO;
import com.gwangju3.bookforest.dto.user.UserDTO;
import com.gwangju3.bookforest.dto.user.UserRankingDTO;

public class CommentMapper {
    public static CommentDTO toDTO(Comment comment) {
        User user = comment.getUser();

        UserDTO userDTO = new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getNickname()
        );


        return new CommentDTO(
                comment.getId(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getUpdatedAt(),
                userDTO
        );
    }
}
