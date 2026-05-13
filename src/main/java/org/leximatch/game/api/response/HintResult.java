package org.leximatch.game.api.response;

public record HintResult(
        String input,
        String dist,
        String ranking
) {

}
