package org.leximatch.game.api;

import lombok.RequiredArgsConstructor;
import org.leximatch.game.api.response.HomeRecordResponse;
import org.leximatch.game.application.service.RecordService;
import org.leximatch.game.common.api.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/record")
public class RecordController {
    private final RecordService recordService;

    @GetMapping
    public Api<HomeRecordResponse> getRecord(
            @RequestParam("device_id") String deviceId
    ) {
        return Api.OK(recordService.getRecord(deviceId));
    }
}
