package com.github.gustavopm1.gotcamel.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonUtils {

    private static ObjectMapper mapper;

    public static synchronized ObjectMapper getDefaultObjectMapper() {
        if(mapper == null) {
            mapper = new ObjectMapper();
            if(mapper == null)
                mapper = new ObjectMapper();
        }
        return mapper;
    }

    public static JsonNode getNode(Object object) throws IOException {
        return getDefaultObjectMapper().readTree(getDefaultObjectMapper().writeValueAsString(object));
    }
}
