package org.leximatch.game.api.response;

public record InitialHintResult(
        Boolean isSuccess,
        String initial
) {
}
