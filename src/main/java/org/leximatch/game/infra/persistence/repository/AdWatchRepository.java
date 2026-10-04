package org.leximatch.game.infra.persistence.repository;

import org.leximatch.game.domain.entity.AdWatchEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface AdWatchRepository extends JpaRepository<AdWatchEntity, Long> {

    Optional<AdWatchEntity> findByDeviceIdAndWatchDate(
            String deviceId,
            LocalDate watchDate
    );
}