package org.leximatch.game.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table(
        name = "ad_watch",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_ad_watch_device_date",
                        columnNames = {"device_id", "watch_date"}
                )
        }
)
public class AdWatchEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "device_id", nullable = false, length = 100)
    private String deviceId;

    @Column(name = "watch_date", nullable = false)
    private LocalDate watchDate;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public AdWatchEntity(
            String deviceId,
            LocalDate watchDate,
            LocalDateTime createdAt
    ) {
        this.deviceId = deviceId;
        this.watchDate = watchDate;
        this.createdAt = createdAt;
    }
}