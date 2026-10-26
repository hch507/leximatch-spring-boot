package org.leximatch.game.domain.policy.score;


import org.leximatch.game.domain.model.GameMode;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
public class ScorePolicy {

    private static final ZoneId KST = ZoneId.of("Asia/Seoul");

    /**
     * 시간대에 따른 시간 점수
     */
    public int calculateTimeScore(LocalDateTime solvedAt, GameMode mode) {
        int baseScore;

        int hour = solvedAt.atZone(KST).getHour();

        if (hour < 6) {
            baseScore = 50;
        } else if (hour < 12) {
            baseScore = 40;
        } else if (hour < 18) {
            baseScore = 30;
        } else {
            baseScore = 20;
        }

        return mode == GameMode.HARD ? baseScore * 2 : baseScore;
    }

    /**
     * 정답 순위에 따른 랭킹 점수
     */
    public int calculateRankingScore(long rank, GameMode mode) {
        if (rank < 1) {
            return 0;
        }

        int baseScore = (int) Math.max(0, 51 - rank);

        return mode == GameMode.HARD ? baseScore * 2 : baseScore;
    }
}
