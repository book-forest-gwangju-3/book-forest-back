package com.gwangju3.bookforest.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
public class Tier {

    @Id
    @GeneratedValue
    @Column(name = "tier_id")
    private Long id;

    @OneToOne(mappedBy = "tier", fetch = FetchType.LAZY)
    private User user;

    @Enumerated(EnumType.STRING)
    private TierName name;

    private Integer exp;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
