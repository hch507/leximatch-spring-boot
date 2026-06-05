package org.leximatch.game.common.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ConfigurationProperties(prefix = "app.version")
@Component
public class VersionProperties {

    private String minVersion;
    private String latestVersion;
}