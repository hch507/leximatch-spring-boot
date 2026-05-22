package org.leximatch.game.domain.policy.hint;

import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

@Component
public class HintRankSelector {

    public HintRankRange select() {
        int value = ThreadLocalRandom.current().nextInt(1, 101);

        int accumulated = 0;

        for (HintTier tier : HintTier.values()) {
            accumulated += tier.getProbability();

            if (value <= accumulated) {
                return new HintRankRange(
                        tier.getMinRank(),
                        tier.getMaxRank()
                );
            }
        }

        return new HintRankRange(301, 500);
    }
}