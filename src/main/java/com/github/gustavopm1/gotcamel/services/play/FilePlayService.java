package com.github.gustavopm1.gotcamel.services.play;

import org.apache.camel.Body;
import org.springframework.stereotype.Service;

@Service
public class FilePlayService {

    public String toUpperCase(@Body String fileContent){
        return fileContent.toUpperCase();
    }
}
