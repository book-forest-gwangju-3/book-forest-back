package com.gwangju3.bookforest.repository;

import com.gwangju3.bookforest.domain.Comment;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommentRepository {

    private final EntityManager em;

    public void save(Comment comment) {
        em.persist(comment);
    }

    public Comment findCommentById(Long commentId) {
        return em.find(Comment.class, commentId);
    }

    public void delete(Comment comment) {
        em.remove(comment);
    }
}
