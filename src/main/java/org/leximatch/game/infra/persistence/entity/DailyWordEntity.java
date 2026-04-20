package org.leximatch.game.infra.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "daily_word")
public class DailyWordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "word_id", nullable = false)
    private WordEntity word;

    @Column(nullable = false, unique = true)
    private LocalDate date;


    public DailyWordEntity(WordEntity word, LocalDate date) {
        this.word = word;
        this.date = date;
    }
}
