package org.leximatch.game.infra.external;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.leximatch.game.common.api.Api;
import org.leximatch.game.common.error.ErrorCode;
import org.leximatch.game.common.exception.ApiException;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.util.UriBuilder;

import java.net.URI;
import java.util.function.Function;
@Slf4j
@Component
@RequiredArgsConstructor
public class FastApiClientSupport {

    private final WebClient webClient;


    public <T> T get(
            String path,
            Function<UriBuilder, URI> uriFunction,
            ParameterizedTypeReference<Api<T>> typeRef
    ) {

        long start = System.currentTimeMillis();

        try {
            log.info("[FastAPI 요청] path={}", path);

            Api<T> response = webClient.get()
                    .uri(uriFunction)
                    .retrieve()
                    .bodyToMono(typeRef)
                    .block();

            log.info("[FastAPI 성공] path={}, elapsed={}ms",
                    path,
                    System.currentTimeMillis() - start
            );

            return extractBody(response);

        } catch (WebClientRequestException e) {

            log.error("[FastAPI 연결 실패] path={}, elapsed={}ms",
                    path,
                    System.currentTimeMillis() - start,
                    e
            );

            throw new ApiException(
                    ErrorCode.FAST_API_TIMEOUT,
                    "FastAPI 서버 연결 실패"
            );

        } catch (WebClientResponseException e) {

            log.error("[FastAPI HTTP 오류] path={}, elapsed={}ms, status={}, body={}",
                    path,
                    System.currentTimeMillis() - start,
                    e.getStatusCode(),
                    e.getResponseBodyAsString(),
                    e
            );

            throw new ApiException(
                    ErrorCode.FAST_API_ERROR,
                    "FastAPI 호출 실패"
            );

        } catch (ApiException e) {
            throw e;  // ⭐ 그대로 전달
        } catch (Exception e) {

            log.error("[FastAPI 알 수 없는 오류] path={}, elapsed={}ms",
                    path,
                    System.currentTimeMillis() - start,
                    e
            );

            throw new ApiException(
                    ErrorCode.FAST_API_ERROR,
                    "외부 서버 오류"
            );
        }
    }

    private <T> T extractBody(Api<T> response) {
        if (response == null || response.getResult() == null) {
            throw new ApiException(ErrorCode.FAST_API_INVALID_RESPONSE);
        }

        int resultCode = response.getResult().getResultCode();
        String description = response.getResult().getResultDescription();

        return switch (resultCode) {
            case 200 -> response.getBody();
            case 4003 -> throw new ApiException(ErrorCode.WORD_NOT_IN_DICTIONARY, description);
            default -> throw new ApiException(ErrorCode.FAST_API_ERROR, description);
        };
    }
}
