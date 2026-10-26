package org.leximatch.game.application.service;

//노말·하드 정답 처리와 월간 점수 적립을 하나의 트랜잭션으로 묶는 역할

import lombok.RequiredArgsConstructor;
import org.leximatch.game.domain.dto.CorrectAnswerResult;
import org.leximatch.game.domain.dto.ScoreResult;
import org.leximatch.game.domain.model.GameMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CorrectAnswerTransactionService {

    private final DailyCorrectService dailyCorrectService;
    private final DailyHardCorrectService dailyHardCorrectService;
    private final ScoreService scoreService;

    @Transactional
    public CorrectAnswerResult processNormalCorrect(
            String deviceId
    ) {
        // 1. 일일 정답 기록 및 랭킹 조회
        Long rank = dailyCorrectService.saveCorrect(deviceId);

        // 2. 월간 점수 기록 및 누적
        ScoreResult scoreResult = scoreService.saveScore(
                deviceId,
                GameMode.NORMAL,
                rank
        );

        // 3. 랭킹 및 획득 점수 반환
        return new CorrectAnswerResult(
                rank,
                scoreResult.timeScore(),
                scoreResult.rankingScore()
        );
    }

    @Transactional
    public CorrectAnswerResult processHardCorrect(
            String deviceId
    ) {
        // 1. 하드 모드 일일 정답 기록 및 랭킹 조회
        Long rank = dailyHardCorrectService.saveCorrect(deviceId);

        // 2. 월간 점수 기록 및 누적
        ScoreResult scoreResult = scoreService.saveScore(
                deviceId,
                GameMode.HARD,
                rank
        );


        // 3. 랭킹 및 획득 점수 반환
        return new CorrectAnswerResult(
                rank,
                scoreResult.timeScore(),
                scoreResult.rankingScore()
        );
    }
}
