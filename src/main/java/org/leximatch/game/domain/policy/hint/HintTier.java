package org.leximatch.game.domain.policy.hint;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum HintTier {

    TOP_10(1, 10, 5),
    TOP_50(11, 50, 10),
    TOP_100(51, 100, 20),
    TOP_300(101, 300, 30),
    TOP_500(301, 500, 35);

    private final int minRank;
    private final int maxRank;
    private final int probability;
}