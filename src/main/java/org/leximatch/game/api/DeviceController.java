package org.leximatch.game.api;

import lombok.RequiredArgsConstructor;
import org.leximatch.game.api.request.DeviceRegisterRequest;
import org.leximatch.game.api.response.DeviceRegisterResponse;
import org.leximatch.game.application.service.DeviceService;
import org.leximatch.game.common.api.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/devices")
public class DeviceController {

    private final DeviceService deviceService;

    @PostMapping
    public Api<DeviceRegisterResponse> register(
            @RequestBody DeviceRegisterRequest request
    ) {

        DeviceRegisterResponse response =
                deviceService.register(request);

        return Api.OK(response);
    }
}
