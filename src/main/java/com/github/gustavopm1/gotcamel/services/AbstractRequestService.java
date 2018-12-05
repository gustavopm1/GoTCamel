package com.github.gustavopm1.gotcamel.services;

import com.github.gustavopm1.gotcamel.configuration.GotCamelConfiguration;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public abstract class  AbstractRequestService {

    public static final String API_KEY = "api_key";

    @Autowired
    @Getter
    @Setter
    protected GotCamelConfiguration configuration;

    public abstract String getURL(Map<String, Object> headers);
    public abstract Map<String,String> getHeaders(Map<String, Object> headers);
    public abstract Map<String,String> getParams(Map<String, Object> params);

    protected abstract void setAPIKey(Map<String, String> headers);
    protected abstract String getBaseUrl();

    //TODO Create a ErrorHandler structure to handle the errors
    protected abstract ResponseEntity handleErrors(Exception e);


    public ResponseEntity<String> doGet(Map<String, Object> headers) {

        HttpHeaders httpHeaders = new HttpHeaders();
        getHeaders(headers)
            .entrySet()
            .forEach( entry ->  httpHeaders.add(entry.getKey(),entry.getValue()));
        try {
            return new RestTemplate()
                    .exchange(
                            getBaseUrl().concat(getURL(headers)).concat("?").concat(buildUrl(getParams(headers))),
                            HttpMethod.GET,
                            new HttpEntity(httpHeaders),
                            String.class,
                            buildParams(getParams(headers)));
        }catch (HttpClientErrorException e){
            return handleErrors(e);/*

*/        }
    }

    private String buildUrl(Map<String, String> headers){
        setAPIKey(headers);
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
        setAPIKey(headers);
        return headers;
    }


}
