package org.leximatch.game;

import lombok.RequiredArgsConstructor;
import org.leximatch.game.domain.service.DailyWordService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DailyWordInitializer  implements ApplicationRunner {

    private final DailyWordService dailyWordService;

    @Override
    public void run(ApplicationArguments args) {
        dailyWordService.createTodayWordIfAbsent();
    }
}