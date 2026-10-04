package org.leximatch.game.application.service;

import lombok.RequiredArgsConstructor;
import org.leximatch.game.domain.entity.AdWatchEntity;
import org.leximatch.game.infra.persistence.repository.AdWatchRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdWatchService {

    private static final ZoneId KST = ZoneId.of("Asia/Seoul");

    private final AdWatchRepository adWatchRepository;

    @Transactional
    public void saveAdWatch(String deviceId) {

        LocalDate today = LocalDate.now(KST);

        if (adWatchRepository.findByDeviceIdAndWatchDate(deviceId, today).isPresent()) {
            return;
        }

        AdWatchEntity adWatch = new AdWatchEntity(
                deviceId,
                today,
                LocalDateTime.now(KST)
        );

        adWatchRepository.save(adWatch);
    }

    public boolean hasWatchedToday(String deviceId) {

        LocalDate today = LocalDate.now(KST);

        return adWatchRepository
                .findByDeviceIdAndWatchDate(deviceId, today)
                .isPresent();
    }
}