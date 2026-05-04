package org.leximatch.game.infra.external.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SimilarityResponse {

    private String keyword;
    private String userInput;
    private String dist;
    private String ranking;
}
