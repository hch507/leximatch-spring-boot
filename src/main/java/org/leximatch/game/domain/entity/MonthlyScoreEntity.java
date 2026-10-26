package org.leximatch.game.domain.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table(
        name = "monthly_score",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_monthly_score_device_month",
                        columnNames = {
                                "device_id",
                                "score_month"
                        }
                )
        },
        indexes = {
                @Index(
                        name = "idx_monthly_score_ranking",
                        columnList = "score_month, total_score"
                )
        }
)
public class MonthlyScoreEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            name = "device_id",
            nullable = false,
            length = 100
    )
    private String deviceId;

    @Column(
            name = "score_month",
            nullable = false,
            length = 7
    )
    private String scoreMonth;

    @Column(
            name = "total_score",
            nullable = false
    )
    private int totalScore;

    @Column(
            name = "normal_score",
            nullable = false
    )
    private int normalScore;

    @Column(
            name = "hard_score",
            nullable = false
    )
    private int hardScore;

    @Column(
            name = "normal_correct_count",
            nullable = false
    )
    private int normalCorrectCount;

    @Column(
            name = "hard_correct_count",
            nullable = false
    )
    private int hardCorrectCount;

    @CreationTimestamp
    @Column(
            name = "created_at",
            nullable = false,
            updatable = false
    )
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(
            name = "updated_at",
            nullable = false
    )
    private LocalDateTime updatedAt;

    public MonthlyScoreEntity(
            String deviceId,
            String scoreMonth
    ) {
        this.deviceId = deviceId;
        this.scoreMonth = scoreMonth;
        this.totalScore = 0;
        this.normalScore = 0;
        this.hardScore = 0;
        this.normalCorrectCount = 0;
        this.hardCorrectCount = 0;
    }

    public void addNormalScore(int score) {
        this.normalScore += score;
        this.totalScore += score;
        this.normalCorrectCount++;
    }

    public void addHardScore(int score) {
        this.hardScore += score;
        this.totalScore += score;
        this.hardCorrectCount++;
    }
}
