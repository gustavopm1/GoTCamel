package com.github.gustavopm1.gotcamel.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Getter
@Setter
@ConfigurationProperties(prefix = "metric")
@Configuration
public class MetricsProperties {

    private List<ExcludedHeaders> excludedHeaders;


    @Getter
    @Setter
    public static class ExcludedHeaders {
        private String headerName;
    }
}
