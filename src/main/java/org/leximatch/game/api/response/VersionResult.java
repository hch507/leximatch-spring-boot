package org.leximatch.game.api.response;

public record VersionResult(
        String minimumVersion,
        String latestVersion
) {
}