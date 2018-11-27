package com.github.gustavopm1.gotcamel.services.themoviedb.movie;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.gustavopm1.gotcamel.exceptions.themoviedb.movie.MovieNotFoundException;
import com.github.gustavopm1.gotcamel.models.Response;
import com.github.gustavopm1.gotcamel.models.themoviedb.movie.Movie;
import com.github.gustavopm1.gotcamel.services.TheMovieDBAbstractRequestService;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Header;
import org.apache.camel.Headers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.HashMap;
import java.util.Map;

import static com.github.gustavopm1.gotcamel.GotCamelConstants.TYPE_VALUE;

@Service
@Slf4j
public class MovieSearchByIdService extends TheMovieDBAbstractRequestService {

    @Override
    public String getURL(Map<String, Object> headers) {
        return configuration.getServices().getMovieUrl().replace(":id",String.valueOf(headers.get(TYPE_VALUE)));
    }

    @Override
    public Map<String, String> getHeaders(Map<String, Object> headers) {
        return new HashMap<>();
    }

    public Map<String,String> getParams(Map<String, Object> params){
        return new HashMap<>();
    }

    public Response<Movie> getMovieById(@Header (TYPE_VALUE) String id, @Headers Map<String, Object> headers) throws MovieNotFoundException {

        try {
            ResponseEntity<String> response = doGet(headers);

            if (response.getStatusCode().equals(HttpStatus.OK)) {
                try {
                    Movie movie = new ObjectMapper().readValue(response.getBody(), Movie.class);
                    return Response.<Movie>builder()
                            .found(true)
                            .body(movie)
                            .build();
                } catch (Exception e) {
                    log.error("Erro ao parsear filme!", e);
                }
            }

            return Response.<Movie>builder()
                    .found(false)
                    .build();
        } catch (HttpClientErrorException e) {
            log.error("HttpClientErrorException while doing get", e);
            throw new MovieNotFoundException(e.getMessage());
        } catch (Exception e) {
            log.error("Error while doing get", e);
            throw new MovieNotFoundException(e.getMessage());
        }
    }
}
