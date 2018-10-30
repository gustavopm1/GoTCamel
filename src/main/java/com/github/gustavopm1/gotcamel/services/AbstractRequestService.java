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
import java.util.stream.Collectors;

public abstract class AbstractRequestService {

    @Autowired
    @Getter
    private GotCamelConfiguration configuration;


    /*
    * BASEURL terá que ser "https://api.themoviedb.org/3/"
    * Movie: https://api.themoviedb.org/3/movie/<<movieID>>?api_key=<<apiKey>>
    * Search: https://api.themoviedb.org/3/search/movie?api_key=<<apiKey>>&query=<<movieName>>
    * */

    public abstract String getURL(Map<String, Object> headers);
    public abstract Map<String,String> getHeaders(Map<String, Object> headers);
    public abstract Map<String,String> getParams(Map<String, Object> params);


    public ResponseEntity<String> doGet(Map<String, Object> headers){

        HttpHeaders httpHeaders = new HttpHeaders();
        getHeaders(headers)
            .entrySet()
            .forEach( entry ->  httpHeaders.add(entry.getKey(),entry.getValue()));

        return new RestTemplate()
                .exchange(
                    configuration.getBaseUrl().concat(getURL(headers)).concat("?").concat(buildUrl(getParams(headers))),
                    HttpMethod.GET,
                    new HttpEntity(httpHeaders),
                    String.class,
                    buildParams(getParams(headers)));
    }

    private String buildUrl(Map<String, String> headers){
        headers.put("api_key", configuration.getApiKey());
        return String.join(
                "&",
                headers
                .keySet()
                .stream()
                .map(k -> String.format("%s={%s}",k,k))
                .collect(Collectors.toList())
        ).replaceAll(" ","+");
    }

    private Map<String, String> buildParams(Map<String, String> headers) {
        headers.put("api_key", configuration.getApiKey());
        return headers;
    }
}
