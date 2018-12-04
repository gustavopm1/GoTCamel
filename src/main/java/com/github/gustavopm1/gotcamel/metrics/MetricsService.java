package com.github.gustavopm1.gotcamel.metrics;

import com.github.gustavopm1.gotcamel.GotCamelConstants;
import com.github.gustavopm1.gotcamel.configuration.MetricsProperties;
import com.github.gustavopm1.gotcamel.converter.StringConverter;
import io.micrometer.core.instrument.Metrics;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class MetricsService {

    private final String COUNT_METRIC_NAME = "count.route.request";

    private final String TIME_METRIC_NAME = "time.route.request";

    private StringConverter stringConverter;
    private MetricsProperties metricsProperties;

    @Autowired
    public MetricsService(StringConverter stringConverter,
            MetricsProperties metricsProperties) {
        this.stringConverter = stringConverter;
        this.metricsProperties = metricsProperties;
    }

    public Processor count(String... tags) {
        return exchange -> {
            List<String> headerTags = convertHeadersToTags(exchange.getIn().getHeaders(), tags);

//            Map<String, Object> headers =  new HashMap<>();
//            List<String> headerTags = convertHeadersToTags(headers, tags);

            Metrics.counter(COUNT_METRIC_NAME, headerTags.toArray(new String[0])).increment();
        };
    }

    public Processor duration(String... tags) {
        return exchange -> {
            List<String> headerTags = convertHeadersToTags(exchange.getIn().getHeaders(), tags);
            Duration duration = getCalculateDuration(exchange);

            Metrics.timer(TIME_METRIC_NAME, headerTags.toArray(new String[0])).record(duration);
        };
    }

    private List<String> convertHeadersToTags(Map<String, Object> headers, String... tagsParam) {
        List<String> tags = new ArrayList<>(Arrays.asList(tagsParam));
        List<String> headerTags = new ArrayList<>();
        Map<String, Object> includedHeaders = new HashMap<>();

//        metricsProperties.getHeaders().stream()
//                .forEach(h -> includedHeaders.put(h.getHeaderName(), ""));


        metricsProperties.getHeaders().stream().forEach(h -> {

            Object headerValue = headers.get(h.getHeaderName());
            String headerStringValue = stringConverter.convert(headerValue);

            headerTags.add(h.getHeaderName());
            if (headerStringValue != null) {
                headerTags.add(headerStringValue);
            } else {
                headerTags.add("");
            }
        });

//        headers.keySet().stream()
//                .filter(s -> metricsProperties.getHeaders().stream()
//                        .anyMatch(e -> e.getHeaderName().equals(s)))
//                .forEach(s -> {
//                    String stringValue = stringConverter.convert(headers.get(s));
//                    headerTags.add(s);
//                    if (stringValue != null) {
//                        headerTags.add(stringValue);
//                    } else {
//                        headerTags.add("");
//                    }
//                });

        headerTags.addAll(tags);

        return headerTags;
    }

    private Duration getCalculateDuration(Exchange exchange) {
        LocalDateTime start = (LocalDateTime) exchange.getIn().getHeaders().get(GotCamelConstants.ROUTE_START_KEY);
        if (start == null) {
            return null;
        }
        LocalDateTime end = LocalDateTime.now();

        return Duration.between(start, end);
    }

}
