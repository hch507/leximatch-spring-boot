package org.leximatch.game.application.service;

import lombok.RequiredArgsConstructor;
import org.leximatch.game.api.response.GuessResult;
import org.leximatch.game.api.response.HintResult;
import org.leximatch.game.common.util.ElapsedTimeProvider;
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

    public String getTodayAnswer() {
        return dailyWordService.getTodayWord();
    }

    public GuessResult guess(String input) {

        String answer = dailyWordService.getTodayWord();
        SimilarityResponse sim = similarityClient.calculateSimilarity(input, answer);
        return new GuessResult(
                input,
                sim.getDist(),
                sim.getRanking(),
                elapsedTimeProvider.getElapsedTimeFromMidnight()
        );
    }

    public HintResult getHint() {

        String answer = dailyWordService.getTodayWord();
        HintResponse hint = todayHintClient.getTodayHint(answer);
        return new HintResult(
          hint.getWord(),
          hint.getSimilarityScore(),
          hint.getRanking()
        );
    }

}
