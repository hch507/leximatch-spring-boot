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

//    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Seoul")
@Scheduled(cron = "*/20 * * * * *", zone = "Asia/Seoul")
    public void syncTodayWordToFastApi() {
        try {
            String todayWord = dailyWordService.createTodayWordIfAbsent();

            log.info("[오늘의 단어 생성/조회 완료] word={}", todayWord);

            dailyWordSyncClient.syncDailyWord(todayWord);

            log.info("[오늘의 단어 FastAPI 캐싱 완료] word={}", todayWord);
        }catch (Exception e){
            log.error("오늘의 단어 FastAPI 동기화 실패", e);
        }
    }

}
