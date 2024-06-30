package com.gwangju3.bookforest.domain;

import jakarta.persistence.CascadeType;
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

    @OneToOne(mappedBy = "tier", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private User user;

    @Enumerated(EnumType.STRING)
    private TierName tierName = TierName.BRONZE;

    private Integer exp = 0;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public void addEXP(Integer exp) {
        this.exp += exp;
        updateTierName();
    }

    public void subtractEXP(Integer exp) {
        this.exp -= exp;
        updateTierName();
    }

    private void updateTierName() {
        if (exp < 1000) {
            this.tierName = TierName.BRONZE;
        } else if (exp < 10000) {
            this.tierName = TierName.SILVER;
        } else if (exp < 50000) {
            this.tierName = TierName.GOLD;
        } else if (exp < 100000) {
            this.tierName = TierName.PLATINUM;
        } else {
            this.tierName = TierName.DIAMOND;
        }
    }
}
