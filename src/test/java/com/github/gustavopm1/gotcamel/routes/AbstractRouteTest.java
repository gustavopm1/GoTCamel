package com.github.gustavopm1.gotcamel.routes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.gustavopm1.gotcamel.configuration.GotCamelConfiguration;
import com.github.gustavopm1.gotcamel.utils.JsonUtils;
import org.apache.camel.ExchangePattern;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Profile("test")
@ActiveProfiles("route")
public abstract class AbstractRouteTest {

    @Autowired protected ProducerTemplate template;
    @Autowired protected GotCamelConfiguration configuration;

    protected ObjectMapper mapper = JsonUtils.getDefaultObjectMapper();
    protected static final Map<String,Object> NO_HEADERS = new HashMap<>();

    protected <T> T sendMessage(String endpoint, Object body, Map<String,Object> headers,TypeReference clazz) throws IOException {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String jsonResponse = (String)template.sendBodyAndHeaders(
                endpoint,
                ExchangePattern.InOut,
                mapper.writeValueAsString(body),
                headers);

        return mapper.readValue(jsonResponse,clazz);
    }

    protected <T> T sendMessage(String endpoint, Object body, Map<String,Object> headers,Class<T> clazz) throws IOException {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String jsonResponse = (String)template.sendBodyAndHeaders(
                endpoint,
                ExchangePattern.InOut,
                mapper.writeValueAsString(body),
                headers);

        return mapper.readValue(jsonResponse,clazz);
    }

    protected String sendString(String endpoint,String body,Map<String,Object> headers) throws JsonProcessingException {
        return (String) template.sendBodyAndHeaders(
                endpoint,
                ExchangePattern.InOnly,
                body,
                headers);
    }

}
