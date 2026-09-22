package org.leximatch.game.infra.persistence.repository;

import org.leximatch.game.domain.entity.NoticeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface NoticeRepository extends JpaRepository<NoticeEntity, Long> {

    Optional<NoticeEntity> findByNoticeDate(LocalDate noticeDate);

}