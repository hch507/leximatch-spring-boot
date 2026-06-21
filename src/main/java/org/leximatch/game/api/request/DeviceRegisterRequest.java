package org.leximatch.game.api.request;

public record DeviceRegisterRequest(
        String deviceId,
        String fcmToken
) {
}