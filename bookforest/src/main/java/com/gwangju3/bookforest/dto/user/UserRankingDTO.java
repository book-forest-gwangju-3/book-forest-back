package com.gwangju3.bookforest.dto.user;

import com.gwangju3.bookforest.domain.TierName;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserRankingDTO {
    private Long id;
    private String username;
    private String nickname;
    private TierName tierName;
    private Integer exp;
}
