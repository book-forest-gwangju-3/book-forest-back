package com.gwangju3.bookforest.domain.commit;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
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
    @Column(name = "like_id")
    private Long id;

    private Integer exp;

    @CreationTimestamp
    private LocalDateTime createdAt;

}
