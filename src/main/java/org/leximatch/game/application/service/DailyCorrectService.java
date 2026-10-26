package org.leximatch.game.application.service;

import lombok.RequiredArgsConstructor;
import org.leximatch.game.common.error.ErrorCode;
import org.leximatch.game.common.exception.ApiException;
import org.leximatch.game.domain.entity.DailyCorrectEntity;
import org.leximatch.game.domain.entity.DailyWordEntity;
import org.leximatch.game.infra.persistence.repository.DailyCorrectRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

//오늘의 문제를 맞혔는지, 기존 일일 랭킹에서 몇 번째인지 관리
@Service
@RequiredArgsConstructor
@Transactional
public class DailyCorrectService {

    private final DailyCorrectRepository dailyCorrectRepository;
    private final DailyWordService dailyWordService;
    private static final ZoneId KST = ZoneId.of("Asia/Seoul");
    public Long saveCorrect(String deviceId) {
        LocalDateTime now = LocalDateTime.now(KST);
        DailyWordEntity todayDailyWord =
                dailyWordService.getTodayDailyWord();

        // 기록 저장, 있으면 무시
        try {
            dailyCorrectRepository.insertIgnore(
                    todayDailyWord.getId(),
                    deviceId,
                    now
            );
        } catch (DataAccessException e) {
            throw new ApiException(
                    ErrorCode.DAILY_CORRECT_SAVE_FAILED
            );
        }
        Long correctId;
        try {
            correctId = dailyCorrectRepository
                    .findIdByDailyWordIdAndDeviceId(
                            todayDailyWord.getId(),
                            deviceId
                    )
                    .orElseThrow(() ->
                            new ApiException(
                                    ErrorCode.DAILY_CORRECT_SAVE_FAILED
                            )
                    );
        } catch (DataAccessException e) {
            throw new ApiException(
                    ErrorCode.RANK_HISTORY_QUERY_FAILED
            );
        }

        try {
            return dailyCorrectRepository.findRank(
                    todayDailyWord,
                    correctId
            );
        } catch (DataAccessException e) {
            throw new ApiException(
                    ErrorCode.RANK_HISTORY_QUERY_FAILED
            );
        }
    }
}