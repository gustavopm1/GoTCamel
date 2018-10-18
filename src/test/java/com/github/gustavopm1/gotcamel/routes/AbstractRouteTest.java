package com.github.gustavopm1.gotcamel.routes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.gustavopm1.gotcamel.configuration.GotCamelConfiguration;
import org.apache.camel.ExchangePattern;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractRouteTest {

    @Autowired protected ProducerTemplate template;
    @Autowired protected GotCamelConfiguration configuration;

    protected static final Map<String,Object> NO_HEADERS = new HashMap<>();

    protected Object sendMessage(String endpoint,Object body,Map<String,Object> headers) throws JsonProcessingException {
        return template.sendBodyAndHeaders(
                endpoint,
                ExchangePattern.InOut,
                body,
                headers);
    }
}
