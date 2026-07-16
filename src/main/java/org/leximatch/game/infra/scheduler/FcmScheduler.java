package org.leximatch.game.infra.scheduler;

import com.google.firebase.messaging.FirebaseMessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.leximatch.game.api.response.HintResult;
import org.leximatch.game.application.service.FcmService;
import org.leximatch.game.application.service.GameService;
import org.leximatch.game.domain.entity.DeviceEntity;
import org.leximatch.game.infra.persistence.repository.DeviceRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class FcmScheduler {

    private final DeviceRepository deviceRepository;
    private final FcmService fcmService;
    private final GameService gameService;

    @Scheduled(cron = "0 0 12 * * *", zone = "Asia/Seoul")
    public void sendDailyNotification() {
        int failCount = 0;

        List<DeviceEntity> devices =
                deviceRepository.findByFcmTokenIsNotNull();

        for (DeviceEntity device : devices) {
            try {
                fcmService.send(
                        device.getFcmToken(),
                        "Momentle",
                        "오늘의 단어, 어디까지 가까워지셨나요?"
                );
            } catch (Exception e) {
                failCount++;
            }
        }
        log.info(
                "FCM 발송 완료 fail={}",
                failCount
        );
    }

    @Scheduled(cron = "0 0 18 * * *", zone = "Asia/Seoul")
    public void sendHintNotification() {

        HintResult hint = gameService.getEpicHint();
        List<DeviceEntity> devices =
                deviceRepository.findByFcmTokenIsNotNull();

        for (DeviceEntity device : devices) {
            try {
                fcmService.send(
                        device.getFcmToken(),
                        "Momentle",
                        "오늘의 힌트! 단어 : " + hint.input()+ ", 순위 : "+ hint.ranking()
                );
            } catch (FirebaseMessagingException ignored) {

            }
        }

    }
}
