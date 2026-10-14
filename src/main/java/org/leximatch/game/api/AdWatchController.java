package org.leximatch.game.api;

import lombok.RequiredArgsConstructor;
import org.leximatch.game.api.request.AdWatchSaveRequest;
import org.leximatch.game.api.response.AdWatchResponse;
import org.leximatch.game.application.service.AdWatchService;
import org.leximatch.game.common.api.Api;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ad")
public class AdWatchController {

    private final AdWatchService adWatchService;

    @PostMapping("/save")
    public Api<String> saveAdWatch(
            @RequestBody AdWatchSaveRequest request
    ) {
        adWatchService.saveAdWatch(request.deviceId());
        return Api.OK("광고 시청 기록 저장 완료");
    }

    @GetMapping("/today")
    public Api<AdWatchResponse> getAdWatch(
            @RequestParam("device_id") String deviceId
    ) {
        boolean adWatched = adWatchService.hasWatchedToday(deviceId);

        return Api.OK(new AdWatchResponse(adWatched));
    }
}