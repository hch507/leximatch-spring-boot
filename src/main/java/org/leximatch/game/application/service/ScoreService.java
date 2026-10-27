package org.leximatch.game.application.service;


import lombok.RequiredArgsConstructor;
import org.leximatch.game.api.response.MonthlyScoreResponse;
import org.leximatch.game.common.error.ErrorCode;
import org.leximatch.game.common.exception.ApiException;
import org.leximatch.game.domain.dto.ScoreResult;
import org.leximatch.game.domain.entity.MonthlyScoreEntity;
import org.leximatch.game.domain.entity.ScoreHistoryEntity;
import org.leximatch.game.domain.model.GameMode;
import org.leximatch.game.domain.policy.score.ScorePolicy;
import org.leximatch.game.infra.persistence.repository.MonthlyScoreRepository;
import org.leximatch.game.infra.persistence.repository.ScoreHistoryRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;



@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScoreService {

    private final MonthlyScoreRepository monthlyScoreRepository;
    private final ScoreHistoryRepository scoreHistoryRepository;
    private final ScorePolicy scorePolicy;

    private static final ZoneId KST =
            ZoneId.of("Asia/Seoul");

    private static final DateTimeFormatter MONTH_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM");

    @Transactional
    public ScoreResult saveScore(
            String deviceId,
            GameMode mode,
            Long rank
    ) {
        try {
            LocalDateTime now = LocalDateTime.now(KST);
            LocalDate gameDate = now.toLocalDate();
            String scoreMonth = gameDate.format(MONTH_FORMAT);

            // 2. 점수 계산
            int timeScore =
                    scorePolicy.calculateTimeScore(now, mode);

            int rankingScore =
                    scorePolicy.calculateRankingScore(rank, mode);

            int inserted = scoreHistoryRepository.insertIgnore(
                    deviceId,
                    scoreMonth,
                    gameDate,
                    mode.name(),
                    timeScore,
                    rankingScore,
                    timeScore + rankingScore,
                    now
            );
            // 3. 이미 처리된 경우 (재입력, 더블탭 포함) → 0점
            if (inserted == 0) {
                return new ScoreResult(0, 0);
            }

            // 4. 월간 점수 조회 또는 생성
            MonthlyScoreEntity monthlyScore =
                    monthlyScoreRepository
                            .findByDeviceIdAndScoreMonth(
                                    deviceId,
                                    scoreMonth
                            )
                            .orElseGet(() ->
                                    monthlyScoreRepository.save(
                                            new MonthlyScoreEntity(
                                                    deviceId,
                                                    scoreMonth
                                            )
                                    )
                            );

            // 5. 월간 점수 누적
            int totalScore = timeScore + rankingScore;

            switch (mode) {
                case NORMAL ->
                        monthlyScore.addNormalScore(totalScore);
                case HARD ->
                        monthlyScore.addHardScore(totalScore);
            }

            return new ScoreResult(timeScore, rankingScore);

        } catch (DataAccessException e) {
            throw new ApiException(ErrorCode.SCORE_SAVE_FAILED);
        }
    }

    public MonthlyScoreResponse getMyMonthlyScore(String deviceId) {
        String scoreMonth = LocalDate.now(KST).format(MONTH_FORMAT);

        return monthlyScoreRepository
                .findByDeviceIdAndScoreMonth(deviceId, scoreMonth)
                .map(m -> new MonthlyScoreResponse(
                        m.getTotalScore(),
                        monthlyScoreRepository.findRank(scoreMonth, m.getTotalScore())
                ))
                // 이번 달 아직 정답이 없는 사용자: 0점, 순위 없음
                .orElseGet(() -> new MonthlyScoreResponse(0, null));
    }
}