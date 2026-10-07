package org.leximatch.game.infra.persistence.repository;

import org.leximatch.game.domain.entity.DailyHardCorrectEntity;
import org.leximatch.game.domain.entity.DailyHardWordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface DailyHardCorrectRepository
        extends JpaRepository<DailyHardCorrectEntity, Long> {

    @Query(
            value = """
                    SELECT id
                    FROM daily_hard_correct
                    WHERE daily_hard_word_id = :dailyHardWordId
                    AND device_id = :deviceId
                    LIMIT 1
                    """,
            nativeQuery = true
    )
    Optional<Long> findIdByDailyHardWordIdAndDeviceId(
            @Param("dailyHardWordId") Long dailyHardWordId,
            @Param("deviceId") String deviceId
    );

    @Query("""
                select dc
                from DailyHardCorrectEntity dc
                where dc.dailyHardWord.id = :dailyHardWordId
                and dc.deviceId = :deviceId
            """)
    Optional<DailyHardCorrectEntity> findRecord(
            @Param("dailyHardWordId") Long dailyHardWordId,
            @Param("deviceId") String deviceId
    );

    @Query("""
                select count(dc) + 1
                from DailyHardCorrectEntity dc
                where dc.dailyHardWord = :dailyHardWord
                and dc.id < :id
            """)
    long findRank(
            @Param("dailyHardWord") DailyHardWordEntity dailyHardWord,
            @Param("id") Long id
    );

    @Modifying
    @Query(
            value = """
                    INSERT IGNORE INTO daily_hard_correct (
                        daily_hard_word_id,
                        device_id,
                        created_at
                    )
                    VALUES (
                        :dailyHardWordId,
                        :deviceId,
                        :createdAt
                    )
                    """,
            nativeQuery = true
    )
    int insertIgnore(
            @Param("dailyHardWordId") Long dailyHardWordId,
            @Param("deviceId") String deviceId,
            @Param("createdAt") LocalDateTime createdAt
    );
}