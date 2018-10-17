package com.github.gustavopm1.gotcamel.services.play;

import org.apache.camel.Body;
import org.springframework.stereotype.Service;

@Service
public class FilePlayService {

    public String toUpperCase(@Body String fileContent){
        return fileContent.toUpperCase();
    }

    public String greetName(@Body String fileContent){
        return "Hello ".concat(fileContent);
    }

    public String meowCat(@Body String fileContent){
        return fileContent.concat(" - MEOW!");
    }

    public String writeInStats(){
        return "*!@#$-";
    }

}
