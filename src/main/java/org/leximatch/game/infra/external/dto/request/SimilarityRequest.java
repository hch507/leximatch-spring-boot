package org.leximatch.game.infra.external.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SimilarityRequest {

    private String input;
    private String answer;

}
