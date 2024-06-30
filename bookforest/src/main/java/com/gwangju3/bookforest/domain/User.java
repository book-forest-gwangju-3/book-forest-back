package com.gwangju3.bookforest.domain;

import com.gwangju3.bookforest.domain.commit.Commit;
import com.gwangju3.bookforest.domain.like.BookLike;
import com.gwangju3.bookforest.domain.like.BookReviewLike;
import com.gwangju3.bookforest.domain.like.Like;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name="USERS")
public class User {
    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    private String nickname;

    private String profileImage;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "tier_id")
    private Tier tier;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MyBook> myBooks = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<BookReview> BookReviews = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookLike> bookLikes = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookReviewLike> bookReviewLikes = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Commit> commits = new ArrayList<>();

    public User(String username, String password, String nickname, Tier tier) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.tier = tier;
    }

    protected User() {

    }
}
