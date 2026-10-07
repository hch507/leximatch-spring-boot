package org.leximatch.game.infra.persistence.repository;

import org.leximatch.game.domain.entity.HardWordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface HardWordRepository extends JpaRepository<HardWordEntity, Long> {

    @Query(value = "SELECT * FROM hard_word ORDER BY RAND() LIMIT 1", nativeQuery = true)
    Optional<HardWordEntity> findRandomWord();
}