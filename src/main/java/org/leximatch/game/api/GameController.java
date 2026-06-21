package org.leximatch.game.api;

import com.google.firebase.messaging.FirebaseMessagingException;
import lombok.RequiredArgsConstructor;
import org.leximatch.game.api.request.GuessRequest;
import org.leximatch.game.api.response.GuessResult;
import org.leximatch.game.api.response.HintResult;
import org.leximatch.game.application.service.FcmService;
import org.leximatch.game.application.service.GameService;
import org.leximatch.game.common.api.Api;
import org.leximatch.game.infra.external.dto.HintResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/games")
public class GameController {

    private final GameService gameService;
    private final FcmService fcmService;


    @GetMapping("/today")
    public String getTodayWord() {
        return gameService.getTodayAnswer();
    }

    @PostMapping("/guess")
    public Api<GuessResult> guess(
            @RequestBody GuessRequest request
    ) {

        GuessResult result = gameService.guess(request);
        return Api.OK(result);
    }
    @GetMapping("/guess")
    public Api<GuessResult> guessByGet(
            @RequestParam String input
    ) {

        GuessRequest request = new GuessRequest(
                input,
                null
        );

        GuessResult result = gameService.guess(request);

        return Api.OK(result);
    }
    @GetMapping("/hint")
    public Api<HintResult> getHint() {

        HintResult result = gameService.getHint();

        return Api.OK(result);
    }

}
