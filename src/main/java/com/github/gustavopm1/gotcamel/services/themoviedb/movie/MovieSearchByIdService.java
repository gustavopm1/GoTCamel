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

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.github.gustavopm1.gotcamel.GotCamelConstants.TYPE_VALUE;

@Service
public class MovieSearchByIdService extends TheMovieDBAbstractRequestService<Movie> {

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

    @Override
    protected Movie createBody(String responseBody) throws IOException {
        return new ObjectMapper().readValue(responseBody, Movie.class);
    }
}
