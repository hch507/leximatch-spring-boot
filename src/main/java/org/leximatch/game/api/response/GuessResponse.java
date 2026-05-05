package org.leximatch.game.api.response;

public record GuessResponse(
        String input,
        String dist,
        String ranking,
        String elapsedTime
) {

}
