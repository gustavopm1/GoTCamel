package com.github.gustavopm1.gotcamel.marshallers.movie;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.gustavopm1.gotcamel.models.Request;
import com.github.gustavopm1.gotcamel.models.movie.TypeValueData;
import com.github.gustavopm1.gotcamel.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.spi.DataFormat;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class MovieMarshaller implements DataFormat {

    protected ObjectMapper mapper = JsonUtils.getDefaultObjectMapper();

    @Override
    public void marshal(Exchange exchange, Object o, OutputStream outputStream) throws Exception {

    }

    @Override
    public Object unmarshal(Exchange exchange, InputStream inputStream) throws Exception {
        String jsonData = IOUtils.toString(inputStream, StandardCharsets.UTF_8.name());
        JsonNode root = mapper.readTree(jsonData);
        Request<?> request = mapper.convertValue(root,Request.class);

        if(request.getBody() instanceof TypeValueData){
            exchange.getIn().setHeader("MOVIE_NAME", ((TypeValueData)request.getBody()).getValue() );
        }

        return mapper.writeValueAsString(request.getBody());
    }
}
