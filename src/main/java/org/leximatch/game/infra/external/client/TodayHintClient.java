package org.leximatch.game.infra.external.client;


import lombok.RequiredArgsConstructor;
import org.leximatch.game.common.api.Api;
import org.leximatch.game.infra.external.FastApiClientSupport;
import org.leximatch.game.infra.external.dto.HintResponse;
import org.leximatch.game.infra.external.dto.SimilarityResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TodayHintClient {

    private final FastApiClientSupport fastApiClientSupport;

    public HintResponse getTodayHint(
            String input
    ){
        HintResponse response = fastApiClientSupport.get(
                "/hint",
                uriBuilder -> uriBuilder
                        .path("/hint")
                        .queryParam("target_word", input)
                        .build(),
                new ParameterizedTypeReference<Api<HintResponse>>() {}
        );

        return response;
    }
}
