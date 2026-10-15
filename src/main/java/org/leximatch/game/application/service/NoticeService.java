package org.leximatch.game.application.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.leximatch.game.api.response.NoticeResponse;
import org.leximatch.game.domain.entity.NoticeEntity;
import org.leximatch.game.infra.persistence.repository.NoticeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoticeService {

    private static final String DEFAULT_NOTICE =
            "오늘도 모맨틀에\\n도전해보세요!";

    private final NoticeRepository noticeRepository;
    private static final ZoneId KST = ZoneId.of("Asia/Seoul");
    @Transactional(readOnly = true)
    public NoticeResponse getTodayNotice() {

        LocalDate today = LocalDate.now(KST);

        log.info("오늘의 공지 조회 - date={}", today);

        String content = noticeRepository.findByNoticeDate(today)
                .map(NoticeEntity::getContent)
                .orElse(DEFAULT_NOTICE);

        return new NoticeResponse(content);
    }
}