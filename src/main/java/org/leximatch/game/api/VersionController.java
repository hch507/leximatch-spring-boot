package org.leximatch.game.api;

import lombok.RequiredArgsConstructor;
import org.leximatch.game.api.response.VersionResult;
import org.leximatch.game.common.api.Api;
import org.leximatch.game.common.config.VersionProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/version")
public class VersionController {

    private final VersionProperties versionProperties;

    @GetMapping
    public Api<VersionResult> version() {

        return Api.OK(
                new VersionResult(
                        versionProperties.getMinVersion(),
                        versionProperties.getLatestVersion()
                )
        );
    }
}
