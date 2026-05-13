package org.leximatch.game.common.util;

import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Component
public class ElapsedTimeProvider {

    private static final ZoneId KOREA_ZONE = ZoneId.of("Asia/Seoul");

    public String getElapsedTimeFromMidnight() {

        ZonedDateTime now = ZonedDateTime.now(KOREA_ZONE);

        ZonedDateTime midnight = now.toLocalDate()
                .atStartOfDay(KOREA_ZONE);

        Duration duration = Duration.between(midnight, now);

        long seconds = duration.getSeconds();

        long hh = seconds / 3600;
        long mm = (seconds % 3600) / 60;
        long ss = seconds % 60;

        return String.format("%02d:%02d:%02d", hh, mm, ss);
    }
}