package org.leximatch.game.domain.policy.hint;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum HintTier {

    TOP_10(1, 10, 3),
    TOP_50(11, 50, 17),
    TOP_100(51, 100, 30),
    TOP_300(101, 300, 35),
    TOP_500(301, 500, 15);

    private final int minRank;
    private final int maxRank;
    private final int probability;
}