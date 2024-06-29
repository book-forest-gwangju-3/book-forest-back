package com.gwangju3.bookforest.repository;

import com.gwangju3.bookforest.domain.User;
import com.gwangju3.bookforest.domain.commit.Commit;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommitRepository {

    private final EntityManager em;

    public List<Commit> findAllCommitByUser(User user) {
        return em.createQuery("select c from Commit c"
                + " where c.user.id = :userId"
                , Commit.class)
                .setParameter("userId", user.getId())
                .getResultList();
    }

    public void save(Commit commit) {
        em.persist(commit);
    }
}
