package org.leximatch.game.api.response;

public record MonthlyScoreResponse(
        int totalScore,
        Long rank
) {}