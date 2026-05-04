package org.leximatch.game.domain.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.leximatch.game.common.error.ErrorCode;
import org.leximatch.game.common.exception.ApiException;
import org.leximatch.game.domain.entity.DailyWordEntity;
import org.leximatch.game.domain.entity.WordEntity;
import org.leximatch.game.infra.persistence.repository.DailyWordRepository;
import org.leximatch.game.infra.persistence.repository.WordRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class DailyWordService {

    private final DailyWordRepository dailyWordRepository;
    private final WordRepository wordRepository;

    @CacheEvict(value = "dailyWord", allEntries = true)
    @Transactional
    public String createTodayWordIfAbsent() {
        LocalDate today = LocalDate.now();

        return dailyWordRepository.findByDateWithWord(today)
                .map(dailyWord -> dailyWord.getWord().getValue())
                .orElseGet(() -> {
                    WordEntity randomWord = wordRepository.findRandomWord()
                            .orElseThrow(() -> new IllegalStateException("Word Table 조회 실패"));;

                    DailyWordEntity dailyWord = new DailyWordEntity(randomWord, today);
                    DailyWordEntity saved = dailyWordRepository.save(dailyWord);

                    return saved.getWord().getValue();
                });
    }

    @Transactional(readOnly = true)
    @Cacheable("dailyWord")
    public String getTodayWord() {
        return dailyWordRepository.findByDateWithWord(LocalDate.now())
                .map(dw -> dw.getWord().getValue())
                .orElseThrow(() ->  new ApiException(ErrorCode.DAILY_WORD_NOT_FOUND));
    }
}
