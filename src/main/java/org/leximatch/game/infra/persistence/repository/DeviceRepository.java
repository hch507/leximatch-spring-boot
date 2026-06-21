package org.leximatch.game.infra.persistence.repository;

import org.leximatch.game.domain.entity.DeviceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DeviceRepository extends JpaRepository<DeviceEntity, Long> {

    @Modifying
    @Query(
            value = """
                    INSERT INTO device (
                        device_id,
                        fcm_token,
                        created_at,
                        updated_at
                    )
                    VALUES (
                        :deviceId,
                        :fcmToken,
                        NOW(),
                        NOW()
                    )
                    ON DUPLICATE KEY UPDATE
                        fcm_token = VALUES(fcm_token),
                        updated_at = NOW()
                    """,
            nativeQuery = true
    )
    void upsertDevice(
            @Param("deviceId") String deviceId,
            @Param("fcmToken") String fcmToken
    );

    List<DeviceEntity> findByFcmTokenIsNotNull();
}
