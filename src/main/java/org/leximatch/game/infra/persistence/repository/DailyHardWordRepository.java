package org.leximatch.game.infra.persistence.repository;

import org.leximatch.game.domain.entity.DailyHardWordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Optional;

public interface DailyHardWordRepository extends JpaRepository<DailyHardWordEntity, Long> {

    @Query("""
        SELECT d
        FROM DailyHardWordEntity d
        JOIN FETCH d.hardWord
        WHERE d.date = :date
    """)
    Optional<DailyHardWordEntity> findByDateWithHardWord(LocalDate date);
}