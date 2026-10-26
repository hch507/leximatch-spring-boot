package org.leximatch.game.infra.persistence.repository;

import org.leximatch.game.domain.entity.MonthlyScoreEntity;
import org.leximatch.game.domain.entity.ScoreHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

public interface ScoreHistoryRepository
        extends JpaRepository<ScoreHistoryEntity, Long> {

    @Modifying
    @Query(value = """
        INSERT IGNORE INTO score_history
            (device_id, score_month, game_date, mode,
             time_score, ranking_score, total_score, created_at)
        VALUES
            (:deviceId, :scoreMonth, :gameDate, :mode,
             :timeScore, :rankingScore, :totalScore, :createdAt)
        """, nativeQuery = true)
    int insertIgnore(
            @Param("deviceId") String deviceId,
            @Param("scoreMonth") String scoreMonth,
            @Param("gameDate") LocalDate gameDate,
            @Param("mode") String mode,
            @Param("timeScore") int timeScore,
            @Param("rankingScore") int rankingScore,
            @Param("totalScore") int totalScore,
            @Param("createdAt") LocalDateTime createdAt
    );
    boolean existsByDeviceIdAndGameDateAndMode(
            String deviceId,
            LocalDate gameDate,
            String mode
    );
}