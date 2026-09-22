package org.leximatch.game.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table(
        name = "notice",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_notice_date",
                        columnNames = "notice_date"
                )
        }
)
public class NoticeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String content;

    @Column(
            name = "notice_date",
            nullable = false
    )
    private LocalDate noticeDate;

    @CreationTimestamp
    @Column(
            nullable = false,
            updatable = false
    )
    private LocalDateTime createdAt;

    public NoticeEntity(
            String content,
            LocalDate noticeDate
    ) {
        this.content = content;
        this.noticeDate = noticeDate;
    }
}