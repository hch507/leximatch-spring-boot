package org.leximatch.game.infra.persistence.repository;

import org.leximatch.game.domain.entity.MonthlyScoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MonthlyScoreRepository
        extends JpaRepository<MonthlyScoreEntity, Long> {

    Optional<MonthlyScoreEntity> findByDeviceIdAndScoreMonth(
            String deviceId,
            String scoreMonth
    );

    @Query("""
        select count(m) + 1
        from MonthlyScoreEntity m
        where m.scoreMonth = :scoreMonth
        and m.totalScore > :totalScore
        """)
    long findRank(
            @Param("scoreMonth") String scoreMonth,
            @Param("totalScore") int totalScore
    );

}