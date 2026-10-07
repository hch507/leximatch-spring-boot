package org.leximatch.game;

import lombok.RequiredArgsConstructor;
import org.leximatch.game.application.service.DailyHardWordService;
import org.leximatch.game.application.service.DailyWordService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DailyWordInitializer  implements ApplicationRunner {

    private final DailyWordService dailyWordService;
    private final DailyHardWordService dailyHardWordService;

    @Override
    public void run(ApplicationArguments args) {
        dailyWordService.createTodayWordIfAbsent();
        dailyHardWordService.createTodayHardWordIfAbsent();
    }
}