package org.leximatch.game.infra.external.client;


import lombok.RequiredArgsConstructor;
import org.leximatch.game.common.api.Api;
import org.leximatch.game.infra.external.FastApiClientSupport;
import org.leximatch.game.infra.external.dto.HintResponse;
import org.leximatch.game.infra.external.dto.SimilarityResponse;
import org.leximatch.game.infra.external.dto.request.HintRequest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TodayHintClient {

    private final FastApiClientSupport fastApiClientSupport;

    public HintResponse getTodayHint(
            String input,
            int minRank,
            int maxRank
    ){
        HintRequest request = new HintRequest(
                input,
                minRank,
                maxRank
        );

        HintResponse response = fastApiClientSupport.post(
                "/hint",
                request,
                new ParameterizedTypeReference<Api<HintResponse>>() {}
        );

        return response;
    }
}
