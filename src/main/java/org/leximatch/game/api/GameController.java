package org.leximatch.game.api;

import lombok.RequiredArgsConstructor;
import org.leximatch.game.domain.service.GameService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/games")
public class GameController {

    private final GameService gameService;

    @GetMapping("/today")
    public String getTodayWord() {
        return gameService.getTodayAnswer();
    }

    @GetMapping("/guess") // PostMapping -> GetMapping으로 변경
    public void guess(@RequestParam String input) {
        gameService.guess(input);
    }
}
