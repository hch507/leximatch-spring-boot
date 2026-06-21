package org.leximatch.game.application.service;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.leximatch.game.api.request.DeviceRegisterRequest;
import org.leximatch.game.api.response.DeviceRegisterResponse;
import org.leximatch.game.infra.persistence.repository.DeviceRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class DeviceService {
    private final DeviceRepository deviceRepository;

    public DeviceRegisterResponse register(DeviceRegisterRequest request) {
        deviceRepository.upsertDevice(
                request.deviceId(),
                request.fcmToken()
        );
        return new DeviceRegisterResponse(
                request.deviceId()
        );
    }
}
