package com.gwangju3.bookforest.service;

import com.gwangju3.bookforest.domain.User;
import com.gwangju3.bookforest.domain.commit.Commit;
import com.gwangju3.bookforest.resository.TierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TierService {

    private final TierRepository tierRepository;

    public void addTierEXP(User user, Commit commit) {
        user.getTier().addEXP(commit.getExp());
    }

    public void subtractTierEXP(User user, Integer deleteEXP) {
        user.getTier().subtractEXP(deleteEXP);
    }
}
