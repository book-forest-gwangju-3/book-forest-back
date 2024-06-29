package com.gwangju3.bookforest.domain.commit;

import com.gwangju3.bookforest.domain.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "COMMIT_TYPE")
public abstract class Commit {
    @Id
    @GeneratedValue
    @Column(name = "commit_id")
    private Long id;

    private Integer exp;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public void setUser(User user) {
        this.user = user;
        user.getCommits().add(this);
    }

    public Commit(Integer exp) {
        this.exp = exp;
    }

    protected Commit() {

    }
}
