package org.leximatch.game.application.service;

import lombok.RequiredArgsConstructor;
import org.leximatch.game.api.response.GuessResponse;
import org.leximatch.game.common.util.ElapsedTimeProvider;
import org.leximatch.game.infra.external.client.SimilarityClient;
import org.leximatch.game.infra.external.dto.SimilarityResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameService {
    private final DailyWordService dailyWordService;

    private final SimilarityClient similarityClient;
    private final ElapsedTimeProvider elapsedTimeProvider;

    public String getTodayAnswer() {
        return dailyWordService.getTodayWord();
    }

    public GuessResponse guess(String input) {

        String answer = dailyWordService.getTodayWord();
        SimilarityResponse sim = similarityClient.calculateSimilarity(input, answer);
        return new GuessResponse(
                input,
                sim.getDist(),
                sim.getRanking(),
                elapsedTimeProvider.getElapsedTimeFromMidnight()
        );
    }

}
