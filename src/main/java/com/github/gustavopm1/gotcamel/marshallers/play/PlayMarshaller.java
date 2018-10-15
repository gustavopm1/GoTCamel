package com.github.gustavopm1.gotcamel.marshallers.play;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.spi.DataFormat;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;


import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.stream.Collectors;

@Slf4j
@Component
public class PlayMarshaller implements DataFormat {

    @Override
    public void marshal(Exchange exchange, Object o, OutputStream outputStream) throws Exception {

    }

    @Override
    public Object unmarshal(Exchange exchange, InputStream inputStream) throws Exception {
        String lineData = IOUtils.toString(inputStream, StandardCharsets.UTF_8.name());
        return Arrays.stream(lineData.split("\n")).collect(Collectors.toList());

    }
}
