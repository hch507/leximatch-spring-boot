package org.leximatch.game.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameService {
    private final DailyWordService dailyWordService;


    public String getTodayAnswer() {
        return dailyWordService.getTodayWord();
    }
}
