package com.gwangju3.bookforest.service;

import com.gwangju3.bookforest.domain.BookReview;
import com.gwangju3.bookforest.domain.Comment;
import com.gwangju3.bookforest.domain.MyBook;
import com.gwangju3.bookforest.domain.User;
import com.gwangju3.bookforest.domain.commit.BookReviewCommit;
import com.gwangju3.bookforest.domain.commit.CommentCommit;
import com.gwangju3.bookforest.domain.commit.Commit;
import com.gwangju3.bookforest.domain.commit.ReadCommit;
import com.gwangju3.bookforest.repository.CommitRepository;
import com.gwangju3.bookforest.repository.UserRepository;
import com.gwangju3.bookforest.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CommitService {

    private final CommitRepository commitRepository;
    private final UserRepository userRepository;
    private final TierService tierService;

    @Transactional(readOnly = true)
    public List<Commit> findCommitsByUser() {
        User user = userRepository.findByUsername(UserUtil.extractUsername()).get(0);
        return commitRepository.findAllCommitByUser(user);
    }

    public void createBookReviewCommit(BookReview bookReview) {
        User user = userRepository.findByUsername(UserUtil.extractUsername()).get(0);

        Commit commit = new BookReviewCommit(200, bookReview);
        commit.setUser(user);

        commitRepository.save(commit);
        tierService.addTierEXP(user, commit);
    }

    public void createReadCommit(Integer readPage, MyBook mybook) {
        User user = userRepository.findByUsername(UserUtil.extractUsername()).get(0);

        Commit commit = new ReadCommit(readPage, mybook);
        commit.setUser(user);

        commitRepository.save(commit);
        tierService.addTierEXP(user, commit);

    }

    public void createCommentCommit(Comment comment) {
        User user = userRepository.findByUsername(UserUtil.extractUsername()).get(0);

        Commit commit = new CommentCommit(10, comment);
        commit.setUser(user);

        commitRepository.save(commit);
        tierService.addTierEXP(user, commit);


    }
}
