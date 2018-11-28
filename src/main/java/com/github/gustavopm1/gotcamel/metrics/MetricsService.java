package com.github.gustavopm1.gotcamel.metrics;

import io.micrometer.core.instrument.Metrics;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MetricsService {

    public Processor counterIncrement(String metricName, String tag) {
        //        Metrics.counter(metricName, "result", tag).increment();
        return exchange ->
                Metrics.counter(metricName, "result", tag).increment();
    }

}
