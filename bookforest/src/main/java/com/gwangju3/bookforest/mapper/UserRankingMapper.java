package com.gwangju3.bookforest.mapper;

import com.gwangju3.bookforest.domain.User;
import com.gwangju3.bookforest.dto.user.UserRankingDTO;

public class UserRankingMapper {
    public static UserRankingDTO entityToDTO(User user) {
        return new UserRankingDTO(
                user.getId(),
                user.getUsername(),
                user.getNickname(),
                user.getTier().getTierName(),
                user.getTier().getExp()
        );
    }
}
