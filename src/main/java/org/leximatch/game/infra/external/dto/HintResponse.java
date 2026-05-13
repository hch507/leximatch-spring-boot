package org.leximatch.game.infra.external.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HintResponse {
    private String word;
    private String dist;
    private String ranking;
}
