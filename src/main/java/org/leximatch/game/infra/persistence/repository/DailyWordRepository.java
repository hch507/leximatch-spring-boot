package org.leximatch.game.infra.persistence.repository;

import org.leximatch.game.domain.entity.DailyWordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface DailyWordRepository extends JpaRepository<DailyWordEntity, Long> {
    @Query("""
        select d
        from DailyWordEntity d
        join fetch d.word
        where d.date = :date
    """)
    Optional<DailyWordEntity> findByDateWithWord(@Param("date") LocalDate date);
}
