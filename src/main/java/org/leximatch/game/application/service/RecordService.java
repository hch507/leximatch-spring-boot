package org.leximatch.game.application.service;

import lombok.RequiredArgsConstructor;
import org.leximatch.game.api.response.HomeRecordResponse;
import org.leximatch.game.api.response.RecordResponse;
import org.leximatch.game.common.util.ElapsedTimeProvider;
import org.leximatch.game.domain.entity.DailyCorrectEntity;
import org.leximatch.game.domain.entity.DailyHardCorrectEntity;
import org.leximatch.game.domain.entity.DailyHardWordEntity;
import org.leximatch.game.domain.entity.DailyWordEntity;
import org.leximatch.game.infra.persistence.repository.DailyCorrectRepository;
import org.leximatch.game.infra.persistence.repository.DailyHardCorrectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;

//Todo : db조회가 많은 듯
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecordService {

    private static final ZoneId KST = ZoneId.of("Asia/Seoul");

    private final DailyCorrectRepository dailyCorrectRepository;
    private final DailyHardCorrectRepository dailyHardCorrectRepository;
    private final ElapsedTimeProvider elapsedTimeProvider;
    private final DailyWordService dailyWordService;
    private final DailyHardWordService dailyHardWordService;

    public HomeRecordResponse getRecord(String deviceId) {

        LocalDate recordDate = LocalDate.now(KST);

        DailyWordEntity todayDailyWord =
                dailyWordService.getTodayDailyWord();

        Optional<DailyCorrectEntity> normal =
                dailyCorrectRepository.findRecord(
                        todayDailyWord.getId(),
                        deviceId
                );

        if (normal.isEmpty()) {
            return new HomeRecordResponse(
                    recordDate,
                    null,
                    null
            );
        }

        DailyCorrectEntity normalCorrect = normal.get();

        long normalRank =
                dailyCorrectRepository.findRank(
                        todayDailyWord,
                        normalCorrect.getId()
                );

        RecordResponse normalRecord =
                new RecordResponse(
                        String.valueOf(normalRank),
                        elapsedTimeProvider.getElapsedTime(
                                normalCorrect.getCreatedAt()
                        )
                );

        // Hard 조회
        DailyHardWordEntity todayDailyHardWord =
                dailyHardWordService.getTodayDailyHardWord();

        Optional<DailyHardCorrectEntity> hard =
                dailyHardCorrectRepository.findRecord(
                        todayDailyHardWord.getId(),
                        deviceId
                );

        RecordResponse hardRecord = hard
                .map(hardCorrect -> {

                    long hardRank =
                            dailyHardCorrectRepository.findRank(
                                    todayDailyHardWord,
                                    hardCorrect.getId()
                            );

                    return new RecordResponse(
                            String.valueOf(hardRank),
                            elapsedTimeProvider.getElapsedTime(
                                    hardCorrect.getCreatedAt()
                            )
                    );
                })
                .orElse(null);

        return new HomeRecordResponse(
                recordDate,
                normalRecord,
                hardRecord
        );
    }
}