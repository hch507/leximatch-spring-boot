package org.leximatch.game.infra.external.client;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.leximatch.game.common.api.Api;
import org.leximatch.game.infra.external.dto.SimilarityRequest;
import org.leximatch.game.infra.external.dto.SimilarityResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
@Slf4j
@Component
@RequiredArgsConstructor
public class SimilarityClient {

    private final WebClient webClient;


    public SimilarityResponse calculateSimilarity(String input, String answer) {

        log.info("[FastAPI 요청] input: {}, answer: {}", input, answer);
        SimilarityRequest request = new SimilarityRequest(input, answer);

        Api<SimilarityResponse> response = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/similarity")
                        .queryParam("text1", input)
                        .queryParam("text2", answer)
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Api<SimilarityResponse>>() {})
                .block(); // 동기 처리
        if (response != null && response.getBody() != null) {
            SimilarityResponse data = response.getBody();
            log.info("[FastAPI 응답 완료] 결과 데이터: {},{},", data.getDist(), data.getRanking());
            return data;
        } else {
            log.warn("[FastAPI 응답 실패] 응답 객체가 비어있습니다.");
            return null;
        }

    }
}