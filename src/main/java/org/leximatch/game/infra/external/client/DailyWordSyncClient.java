package org.leximatch.game.infra.external.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.leximatch.game.common.api.Api;
import org.leximatch.game.infra.external.FastApiClientSupport;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Component
@RequiredArgsConstructor
public class DailyWordSyncClient {


    private final FastApiClientSupport fastApiClientSupport;

    public void syncDailyWord(String input) {

        log.info("[FastAPI 요청] input: {}", input);

        fastApiClientSupport.get(
                "/cache/warmup",
                uriBuilder -> uriBuilder
                        .path("/cache/warmup")
                        .queryParam("target_word", input)
                        .build(),
                new ParameterizedTypeReference<Api<Void>>() {
                }
        );
    }
}
