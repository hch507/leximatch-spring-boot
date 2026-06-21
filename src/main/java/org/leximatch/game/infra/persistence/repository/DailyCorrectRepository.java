package org.leximatch.game.infra.persistence.repository;

import org.leximatch.game.domain.entity.DailyCorrectEntity;
import org.leximatch.game.domain.entity.DailyWordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DailyCorrectRepository
        extends JpaRepository<DailyCorrectEntity, Long> {

    @Query(
            value = """
                SELECT id
                FROM daily_correct
                WHERE daily_word_id = :dailyWordId
                AND device_id = :deviceId
                LIMIT 1
                """,
            nativeQuery = true
    )
    Optional<Long> findIdByDailyWordIdAndDeviceId(
            @Param("dailyWordId") Long dailyWordId,
            @Param("deviceId") String deviceId
    );

    @Query("""
                select count(dc) + 1
                from DailyCorrectEntity dc
                where dc.dailyWord = :dailyWord
                and dc.id < :id
            """)
    long findRank(
            @Param("dailyWord") DailyWordEntity dailyWord,
            @Param("id") Long id
    );

    @Modifying
    @Query(
            value ="""
                INSERT IGNORE INTO daily_correct (
                    daily_word_id,
                    device_id,
                    created_at
                )
                VALUES (
                    :dailyWordId,
                    :deviceId,
                    NOW()
                )
                """,
            nativeQuery = true
    )
    int insertIgnore(
            @Param("dailyWordId") Long dailyWordId,
            @Param("deviceId") String deviceId
    );
}