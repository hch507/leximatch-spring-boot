package org.leximatch.game.infra.persistence.repository;

import org.leximatch.game.domain.entity.DailyWordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface DailyWordRepository extends JpaRepository<DailyWordEntity, Long> {
    Optional<DailyWordEntity> findByDate(LocalDate date);
}
