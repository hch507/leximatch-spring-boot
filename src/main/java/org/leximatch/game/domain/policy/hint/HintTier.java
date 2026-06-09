package org.leximatch.game.domain.policy.hint;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum HintTier {

    TOP_10(1, 10, 3),
    TOP_50(11, 50, 5),
    TOP_100(51, 100, 15),
    TOP_300(101, 300, 50),
    TOP_500(301, 500, 27);

    private final int minRank;
    private final int maxRank;
    private final int probability;
}