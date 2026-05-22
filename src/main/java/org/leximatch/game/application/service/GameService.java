package org.leximatch.game.application.service;

import lombok.RequiredArgsConstructor;
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

    private final SimilarityClient similarityClient;
    private final TodayHintClient todayHintClient;
    private final ElapsedTimeProvider elapsedTimeProvider;
    private final HintRankSelector hintRankSelector;

    public String getTodayAnswer() {
        return dailyWordService.getTodayWord();
    }

    public GuessResult guess(String input) {

        String answer = dailyWordService.getTodayWord();
        SimilarityResponse sim = similarityClient.calculateSimilarity(answer, input);
        return new GuessResult(
                input,
                sim.getDist(),
                sim.getRanking(),
                elapsedTimeProvider.getElapsedTimeFromMidnight()
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

}
