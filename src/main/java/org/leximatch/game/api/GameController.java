package org.leximatch.game.api;

import lombok.RequiredArgsConstructor;
import org.leximatch.game.api.response.GuessResult;
import org.leximatch.game.api.response.HintResult;
import org.leximatch.game.application.service.GameService;
import org.leximatch.game.common.api.Api;
import org.leximatch.game.infra.external.dto.HintResponse;
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
    public Api<GuessResult> guess(@RequestParam String input) {
        GuessResult result = gameService.guess(input);
        return Api.OK(result);
    }


    @GetMapping("/hint")
    public Api<HintResult> getHint() {

        HintResult result = gameService.getHint();

        return Api.OK(result);
    }

}
