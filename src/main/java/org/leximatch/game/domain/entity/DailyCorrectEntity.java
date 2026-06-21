package org.leximatch.game.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table(
        name = "daily_correct",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_daily_correct_word_device",
                        columnNames = {
                                "daily_word_id",
                                "device_id"
                        }
                )
        }
)
public class DailyCorrectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "daily_word_id",
            nullable = false
    )
    private DailyWordEntity dailyWord;

    @Column(
            nullable = false,
            length = 100
    )
    private String deviceId;

    @CreationTimestamp
    @Column(
            nullable = false,
            updatable = false
    )
    private LocalDateTime createdAt;

    public DailyCorrectEntity(
            DailyWordEntity dailyWord,
            String deviceId
    ) {
        this.dailyWord = dailyWord;
        this.deviceId = deviceId;
    }
}