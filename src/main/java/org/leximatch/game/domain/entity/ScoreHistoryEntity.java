package org.leximatch.game.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table(
        name = "score_history",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_score_history_device_date_mode",
                        columnNames = {
                                "device_id",
                                "game_date",
                                "mode"
                        }
                )
        },
        indexes = {
                @Index(
                        name = "idx_score_history_device_month",
                        columnList = "device_id, score_month"
                )
        }
)
public class ScoreHistoryEntity {

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
            name = "game_date",
            nullable = false
    )
    private LocalDate gameDate;

    @Column(
            nullable = false,
            length = 10
    )
    private String mode;

    @Column(
            name = "time_score",
            nullable = false
    )
    private int timeScore;

    @Column(
            name = "ranking_score",
            nullable = false
    )
    private int rankingScore;

    @Column(
            name = "total_score",
            nullable = false
    )
    private int totalScore;

    @CreationTimestamp
    @Column(
            name = "created_at",
            nullable = false,
            updatable = false
    )
    private LocalDateTime createdAt;

    public ScoreHistoryEntity(
            String deviceId,
            String scoreMonth,
            LocalDate gameDate,
            String mode,
            int timeScore,
            int rankingScore
    ) {
        this.deviceId = deviceId;
        this.scoreMonth = scoreMonth;
        this.gameDate = gameDate;
        this.mode = mode;
        this.timeScore = timeScore;
        this.rankingScore = rankingScore;
        this.totalScore = timeScore + rankingScore;
    }
}