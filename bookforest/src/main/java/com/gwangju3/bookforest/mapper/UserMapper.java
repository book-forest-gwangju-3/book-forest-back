package com.gwangju3.bookforest.mapper;

import com.gwangju3.bookforest.domain.User;
import com.gwangju3.bookforest.dto.user.UserDTO;

public class UserMapper {
    public static UserDTO entityToDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getNickname()
        );
    }
}
