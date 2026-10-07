package org.leximatch.game.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "daily_hard_word")
public class DailyHardWordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hard_word_id", nullable = false)
    private HardWordEntity hardWord;

    @Column(nullable = false, unique = true)
    private LocalDate date;

    public DailyHardWordEntity(HardWordEntity hardWord, LocalDate date) {
        this.hardWord = hardWord;
        this.date = date;
    }
}