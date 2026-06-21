package org.leximatch.game.application.service;

import lombok.RequiredArgsConstructor;
import org.leximatch.game.domain.entity.DailyCorrectEntity;
import org.leximatch.game.domain.entity.DailyWordEntity;
import org.leximatch.game.infra.persistence.repository.DailyCorrectRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class DailyCorrectService {

    private final DailyCorrectRepository dailyCorrectRepository;
    private final DailyWordService dailyWordService;

    public Long saveCorrect(String deviceId) {
        DailyWordEntity todayDailyWord =
                dailyWordService.getTodayDailyWord();

        // 기록 저장, 있으면 무시
        dailyCorrectRepository.insertIgnore(
                todayDailyWord.getId(),
                deviceId
        );

        Long correctId =
                dailyCorrectRepository
                        .findIdByDailyWordIdAndDeviceId(
                                todayDailyWord.getId(),
                                deviceId
                        )
                        .orElseThrow(() ->
                                new IllegalStateException(
                                        "정답 기록 조회 실패"
                                ));

        return dailyCorrectRepository.findRank(
                todayDailyWord,
                correctId
        );
    }
}