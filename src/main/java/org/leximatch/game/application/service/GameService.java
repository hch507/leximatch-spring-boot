package org.leximatch.game.application.service;

import lombok.RequiredArgsConstructor;
import org.leximatch.game.api.request.GuessRequest;
import org.leximatch.game.api.response.GuessResult;
import org.leximatch.game.api.response.HintResult;
import org.leximatch.game.common.util.ElapsedTimeProvider;
import org.leximatch.game.domain.policy.hint.HintRankRange;
import org.leximatch.game.domain.policy.hint.HintRankSelector;
import org.leximatch.game.infra.external.client.SimilarityClient;
import org.leximatch.game.infra.external.client.TodayHintClient;
import org.leximatch.game.infra.external.dto.HintResponse;
import org.leximatch.game.infra.external.dto.SimilarityResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameService {
    private final DailyWordService dailyWordService;
    private final DailyCorrectService dailyCorrectService;

    private final SimilarityClient similarityClient;
    private final TodayHintClient todayHintClient;
    private final ElapsedTimeProvider elapsedTimeProvider;
    private final HintRankSelector hintRankSelector;

    public String getTodayAnswer() {
        return dailyWordService.getTodayWord();
    }

    public GuessResult guess(GuessRequest request) {
        String input = request.input();

        String answer = dailyWordService.getTodayWord();

        SimilarityResponse sim =
                similarityClient.calculateSimilarity(
                        answer,
                        input
                );

        String clearRank = "NOT_CORRECT"; // 정답 아님

        if (sim.getDist().equals("100.0")) {
            try {
                clearRank = dailyCorrectService.saveCorrect(
                        request.deviceId()
                ).toString();
            } catch (Exception e) {
                clearRank = "RANK_SAVE_FAILED"; //정답은 맞앗지만 랭킹 저장 실패
            }
        }

        return new GuessResult(
                input,
                sim.getDist(),
                sim.getRanking(),
                elapsedTimeProvider.getElapsedTimeFromMidnight(),
                clearRank
        );
    }

    public HintResult getHint() {

        String answer = dailyWordService.getTodayWord();
        HintRankRange range = hintRankSelector.select();

        HintResponse hint = todayHintClient.getTodayHint(
                answer,
                range.minRank(),
                range.maxRank()
        );

        return new HintResult(
          hint.getWord(),
          hint.getDist(),
          hint.getRanking()
        );
    }


    public HintResult getEpicHint() {

        String answer = dailyWordService.getTodayWord();

        HintResponse hint = todayHintClient.getTodayHint(
                answer,
                51,
                100
        );

        return new HintResult(
                hint.getWord(),
                hint.getDist(),
                hint.getRanking()
        );
    }
}
