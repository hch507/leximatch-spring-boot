package org.leximatch.game.infra.external.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SimilarityRequest {

    private String input;
    private String answer;

}
