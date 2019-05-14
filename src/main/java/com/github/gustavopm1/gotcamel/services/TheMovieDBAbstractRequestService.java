package com.github.gustavopm1.gotcamel.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.gustavopm1.gotcamel.exceptions.themoviedb.movie.MovieNotFoundException;
import com.github.gustavopm1.gotcamel.models.Response;
import com.github.gustavopm1.gotcamel.models.themoviedb.movie.Movie;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Header;
import org.apache.camel.Headers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;
import java.util.Map;

import static com.github.gustavopm1.gotcamel.GotCamelConstants.TYPE_VALUE;

@Slf4j
public abstract class TheMovieDBAbstractRequestService<T> extends AbstractRequestService {

    /*
     * BASEURL terá que ser "https://api.themoviedb.org/3/"
     * Movie: https://api.themoviedb.org/3/movie/<<movieID>>?api_key=<<apiKeyTMDB>>
     * Search: https://api.themoviedb.org/3/search/movie?api_key=<<apiKeyTMDB>>&query=<<movieName>>
     * */

    protected abstract T createBody(String responseBody) throws IOException;

    protected Response<T> createResponse(T body, boolean found) {
        return Response.<T>builder()
                .found(found)
                .body(body)
                .build();
    }

    public Response<T> requestMovieDBTemplate(@Header(TYPE_VALUE) String parameter, @Headers Map<String, Object> headers) throws MovieNotFoundException {
        try {
            checkAndFixTypeValueFromHeaders(parameter, headers);
            ResponseEntity<String> response = doGet(headers);

            if (isHttpStatusOk(response, HttpStatus.OK)) {
                try {
                    T body = createBody(response.getBody());
                    return createResponse(body, true);
                } catch (Exception e) {
                    log.error("Erro ao parsear filme!", e);
                }
            } else if (isHttpStatusOk(response, HttpStatus.NOT_FOUND)) {
                throw new MovieNotFoundException(response.getStatusCode().getReasonPhrase());
            }

            return createResponse(null, false);
        } catch (HttpClientErrorException e) {
            log.error("HttpClientErrorException while doing get", e);
            throw new MovieNotFoundException(e.getMessage());
        } catch (Exception e) {
            log.error("Error while doing get", e);
            throw new MovieNotFoundException(e.getMessage());
        }
    }

    private void checkAndFixTypeValueFromHeaders(String parameter, Map<String, Object> headers) {
        if (!headers.get(TYPE_VALUE).equals(parameter)) {
            headers.put(TYPE_VALUE, parameter);
        }
    }

    private boolean isHttpStatusOk(ResponseEntity<String> response, HttpStatus ok) {
        return response.getStatusCode().equals(ok);
    }

    @Override
    protected void setAPIKey(Map<String, String> headers) {
        headers.put(API_KEY, configuration.getApiKeyTMDB());
    }

    @Override
    protected String getBaseUrl() {
        return configuration.getBaseUrlTMDB();
    }

    @Override
    protected ResponseEntity handleErrors(Exception e){
        if(e.getMessage().contains("429"))
            return ResponseEntity.status(429).build();
        if(e.getMessage().contains("404"))
            return ResponseEntity.notFound().build();

        return ResponseEntity.noContent().build();
    }


}
