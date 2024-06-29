package com.gwangju3.bookforest.service;

import com.gwangju3.bookforest.domain.User;
import com.gwangju3.bookforest.domain.commit.Commit;
import com.gwangju3.bookforest.repository.UserRepository;
import com.gwangju3.bookforest.resository.TierRepository;
import com.gwangju3.bookforest.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TierService {

    private final TierRepository tierRepository;

    public void updateTierEXP(User user, Commit commit) {
        user.getTier().addEXP(commit.getExp());
    }
}
