package org.leximatch.game.domain.service;

import lombok.RequiredArgsConstructor;
import org.leximatch.game.infra.external.client.SimilarityClient;
import org.leximatch.game.infra.external.dto.SimilarityResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameService {
    private final DailyWordService dailyWordService;

    private final SimilarityClient similarityClient;


    public String getTodayAnswer() {
        return dailyWordService.getTodayWord();
    }

    public SimilarityResponse guess(String input) {

        String answer = dailyWordService.getTodayWord();

        return similarityClient.calculateSimilarity(input, answer);
    }

}
