package org.leximatch.game.api;

import lombok.RequiredArgsConstructor;
import org.leximatch.game.api.response.GuessResponse;
import org.leximatch.game.application.service.GameService;
import org.leximatch.game.common.api.Api;
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
    public Api<GuessResponse> guess(@RequestParam String input) {
        GuessResponse result = gameService.guess(input);
        return Api.OK(result);
    }
}
