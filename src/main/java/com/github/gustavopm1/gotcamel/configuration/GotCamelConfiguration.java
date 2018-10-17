package com.github.gustavopm1.gotcamel.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "com.gotcamel")
@Component
@Data
public class GotCamelConfiguration {

    GotCamelConfigurationRoute routes;
    GotCamelRouteIdentification ids;

}
