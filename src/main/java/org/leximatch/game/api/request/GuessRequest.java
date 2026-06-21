package org.leximatch.game.api.request;

public record GuessRequest(
        String input,
        String deviceId
) {
}