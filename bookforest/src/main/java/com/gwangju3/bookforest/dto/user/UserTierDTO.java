package com.gwangju3.bookforest.dto.user;

import com.gwangju3.bookforest.domain.TierName;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserTierDTO {
    UserDTO user;
    TierName tierName;
    Integer exp;
    Integer position;
    Integer rank;
}
