package org.leximatch.game.api.response;

public record GuessResult(
        String input,
        String dist,
        String ranking,
        String elapsedTime
) {

}
