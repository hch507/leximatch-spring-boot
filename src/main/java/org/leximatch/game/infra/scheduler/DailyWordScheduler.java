package org.leximatch.game.infra.scheduler;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.leximatch.game.domain.service.DailyWordService;
import org.leximatch.game.infra.external.client.DailyWordSyncClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DailyWordScheduler {

    private final DailyWordService dailyWordService;
    private final DailyWordSyncClient dailyWordSyncClient;

    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Seoul")
    public void syncTodayWordToFastApi() {

        String todayWord = dailyWordService.getTodayWord();
        log.info("syncTodayWordToFastApi text {}",todayWord);
        dailyWordSyncClient.syncDailyWord(todayWord);
    }

}
