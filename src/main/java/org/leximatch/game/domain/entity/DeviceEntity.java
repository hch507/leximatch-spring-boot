package org.leximatch.game.domain.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "device")
public class DeviceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            nullable = false,
            unique = true,
            length = 100
    )
    private String deviceId;

    @Column(length = 255)
    private String fcmToken;

    @CreationTimestamp
    @Column(
            nullable = false,
            updatable = false
    )
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public DeviceEntity(
            String deviceId,
            String fcmToken
    ) {
        this.deviceId = deviceId;
        this.fcmToken = fcmToken;
    }

    public void updateFcmToken(
            String fcmToken
    ) {
        this.fcmToken = fcmToken;
    }
}
