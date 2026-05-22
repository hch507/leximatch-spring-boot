package org.leximatch.game.infra.external.dto.request;

public record HintRequest(
        String answer,
        int minRank,
        int maxRank
) {
}
