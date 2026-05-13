package org.leximatch.game.infra.external.client;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.leximatch.game.common.api.Api;
import org.leximatch.game.infra.external.FastApiClientSupport;
import org.leximatch.game.infra.external.dto.SimilarityRequest;
import org.leximatch.game.infra.external.dto.SimilarityResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
@Slf4j
@Component
@RequiredArgsConstructor
public class SimilarityClient {

    private final FastApiClientSupport fastApiClientSupport;

    public SimilarityResponse calculateSimilarity(String input, String answer) {

        SimilarityResponse response = fastApiClientSupport.get(
                "/similarity",
                uriBuilder -> uriBuilder
                        .path("/similarity")
                        .queryParam("text1", input)
                        .queryParam("text2", answer)
                        .build(),
                new ParameterizedTypeReference<Api<SimilarityResponse>>() {}
        );

        return response;
    }
}