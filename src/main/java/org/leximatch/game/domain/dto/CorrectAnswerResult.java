package org.leximatch.game.domain.dto;

public record CorrectAnswerResult(
        Long rank,
        int timeScore,
        int rankingScore
) {
}