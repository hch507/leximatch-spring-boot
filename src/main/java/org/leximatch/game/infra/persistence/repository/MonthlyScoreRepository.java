package org.leximatch.game.infra.persistence.repository;

import org.leximatch.game.domain.entity.MonthlyScoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MonthlyScoreRepository
        extends JpaRepository<MonthlyScoreEntity, Long> {

    Optional<MonthlyScoreEntity> findByDeviceIdAndScoreMonth(
            String deviceId,
            String scoreMonth
    );
}