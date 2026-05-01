package org.leximatch.game.domain.service;

import lombok.RequiredArgsConstructor;
import org.leximatch.game.infra.persistence.repository.DailyWordRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class DailyWordService {

    private final DailyWordRepository dailyWordRepository;

    @Cacheable("dailyWord")
    public String getTodayWord() {
        return dailyWordRepository.findByDateWithWord(LocalDate.now())
                .map(dw -> dw.getWord().getValue())
                .orElseThrow(() -> new RuntimeException("오늘의 단어 없음"));
    }
}
