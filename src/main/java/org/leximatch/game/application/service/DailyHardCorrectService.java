package org.leximatch.game.application.service;

import lombok.RequiredArgsConstructor;
import org.leximatch.game.domain.entity.DailyHardWordEntity;
import org.leximatch.game.domain.entity.DailyWordEntity;
import org.leximatch.game.infra.persistence.repository.DailyCorrectRepository;
import org.leximatch.game.infra.persistence.repository.DailyHardCorrectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
@Transactional
public class DailyHardCorrectService {

    private final DailyHardCorrectRepository dailyHardCorrectRepository;
    private final DailyHardWordService dailyHardWordService;
    private static final ZoneId KST = ZoneId.of("Asia/Seoul");
    public Long saveCorrect(String deviceId) {
        LocalDateTime now = LocalDateTime.now(KST);
        DailyHardWordEntity todayDailyHardWord =
                dailyHardWordService.getTodayDailyHardWord();

        // 기록 저장, 있으면 무시
        dailyHardCorrectRepository.insertIgnore(
                todayDailyHardWord.getId(),
                deviceId,
                now
        );

        Long correctId =
                dailyHardCorrectRepository
                        .findIdByDailyHardWordIdAndDeviceId(
                                todayDailyHardWord.getId(),
                                deviceId
                        )
                        .orElseThrow(() ->
                                new IllegalStateException(
                                        "하드 정답 기록 조회 실패"
                                ));

        return dailyHardCorrectRepository.findRank(
                todayDailyHardWord,
                correctId
        );
    }
}