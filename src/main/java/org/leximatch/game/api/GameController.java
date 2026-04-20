package org.leximatch.game.api;

import lombok.RequiredArgsConstructor;
import org.leximatch.game.domain.service.GameService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/games")
public class GameController {

    private final GameService gameService;

    @GetMapping("/today")
    public String getTodayWord() {
        return gameService.getTodayAnswer();
    }
}
