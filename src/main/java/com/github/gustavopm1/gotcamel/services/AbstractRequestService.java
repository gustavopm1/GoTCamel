package com.github.gustavopm1.gotcamel.services;

import com.github.gustavopm1.gotcamel.configuration.GotCamelConfiguration;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

public abstract class AbstractRequestService {

    @Autowired
    @Getter
    private GotCamelConfiguration configuration;

    private static String BASEURL = "https://api.themoviedb.org/3/movie/";
    public abstract String getURL(Map<String, Object> headers);
    public abstract Map<String,String> getHeaders(Map<String, Object> headers);

    public ResponseEntity<String> doGet(Map<String, Object> headers){

        HttpHeaders httpHeaders = new HttpHeaders();
        getHeaders(headers)
            .entrySet()
            .forEach( entry ->  httpHeaders.add(entry.getKey(),entry.getValue()));

        return new RestTemplate()
                .exchange(
                    BASEURL.concat(getURL(headers)).concat("?api_key=").concat(configuration.getApiKey()),
                    HttpMethod.GET,
                    new HttpEntity(httpHeaders),
                    String.class);
    }

}
