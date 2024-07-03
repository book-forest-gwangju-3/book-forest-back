package com.gwangju3.bookforest.repository;

import com.gwangju3.bookforest.domain.Tier;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TierRepository {

    private final EntityManager em;

    public void save(Tier tier) {
        em.persist(tier);
    }
}
