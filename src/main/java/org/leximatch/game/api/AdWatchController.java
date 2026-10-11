package org.leximatch.game.api;

import lombok.RequiredArgsConstructor;
import org.leximatch.game.api.request.AdWatchSaveRequest;
import org.leximatch.game.api.response.AdWatchResponse;
import org.leximatch.game.application.service.AdWatchService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ad")
public class AdWatchController {

    private final AdWatchService adWatchService;

    @PostMapping("/save")
    public void saveAdWatch(
            @RequestBody AdWatchSaveRequest request
    ) {
        adWatchService.saveAdWatch(request.deviceId());
    }

    @GetMapping("/today")
    public AdWatchResponse getAdWatch(
            @RequestParam("device_id") String deviceId
    ) {
        boolean adWatched = adWatchService.hasWatchedToday(deviceId);

        return new AdWatchResponse(adWatched);
    }
}