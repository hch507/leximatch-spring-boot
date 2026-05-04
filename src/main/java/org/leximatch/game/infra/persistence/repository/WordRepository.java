package org.leximatch.game.infra.persistence.repository;

import org.leximatch.game.domain.entity.WordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface WordRepository extends JpaRepository<WordEntity, Long> {

    @Query(value = "SELECT * FROM word ORDER BY RAND() LIMIT 1", nativeQuery = true)
    Optional<WordEntity> findRandomWord();
}
