package org.leximatch.game.infra.external.client;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.leximatch.game.common.api.Api;
import org.leximatch.game.infra.external.FastApiClientSupport;
import org.leximatch.game.infra.external.dto.HintResponse;
import org.leximatch.game.infra.external.dto.InitialHintResponse;
import org.leximatch.game.infra.external.dto.request.InitialHintRequest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class InitialHintClient {
    private final FastApiClientSupport fastApiClientSupport;

    public InitialHintResponse getInitialHint(String answer) {


        InitialHintRequest request = new InitialHintRequest(answer);


        InitialHintResponse response =
                fastApiClientSupport.post(
                        "/initial-hint",
                        request,
                        new ParameterizedTypeReference<Api<InitialHintResponse>>() {
                        }
                );

        return response;

    }
}
