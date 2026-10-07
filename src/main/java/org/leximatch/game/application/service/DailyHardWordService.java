package org.leximatch.game.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.leximatch.game.common.error.ErrorCode;
import org.leximatch.game.common.exception.ApiException;
import org.leximatch.game.domain.entity.DailyHardWordEntity;
import org.leximatch.game.domain.entity.HardWordEntity;
import org.leximatch.game.infra.persistence.repository.DailyHardWordRepository;
import org.leximatch.game.infra.persistence.repository.HardWordRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;

@Slf4j
@Service
@RequiredArgsConstructor
public class DailyHardWordService {

    private final DailyHardWordRepository dailyHardWordRepository;
    private final HardWordRepository hardWordRepository;

    private static final ZoneId KST = ZoneId.of("Asia/Seoul");

    @CacheEvict(value = "dailyHardWord", allEntries = true, beforeInvocation = true)
    @Transactional
    public String createTodayHardWordIfAbsent() {
        LocalDate today = LocalDate.now(KST);

        log.info("[시간 hardWord={}", today);

        return dailyHardWordRepository.findByDateWithHardWord(today)
                .map(dailyHardWord -> dailyHardWord.getHardWord().getValue())
                .orElseGet(() -> {
                    HardWordEntity randomWord = hardWordRepository.findRandomWord()
                            .orElseThrow(() -> new IllegalStateException("Hard Word Table 조회 실패"));

                    DailyHardWordEntity dailyHardWord = new DailyHardWordEntity(randomWord, today);

                    DailyHardWordEntity saved = dailyHardWordRepository.save(dailyHardWord);

                    return saved.getHardWord().getValue();
                });
    }

    @Transactional(readOnly = true)
    @Cacheable("dailyHardWord")
    public String getTodayHardWord() {
        LocalDate today = LocalDate.now(KST);

        return dailyHardWordRepository.findByDateWithHardWord(today)
                .map(dailyHardWord -> dailyHardWord.getHardWord().getValue())
                .orElseThrow(() ->
                        new ApiException(ErrorCode.DAILY_WORD_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public DailyHardWordEntity getTodayDailyHardWord() {
        LocalDate today = LocalDate.now(KST);

        return dailyHardWordRepository.findByDateWithHardWord(today)
                .orElseThrow(() ->
                        new ApiException(ErrorCode.DAILY_WORD_NOT_FOUND));
    }
}