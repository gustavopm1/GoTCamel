package com.github.gustavopm1.gotcamel.services.themoviedb.movie;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.gustavopm1.gotcamel.exceptions.themoviedb.movie.MovieNotFoundException;
import com.github.gustavopm1.gotcamel.models.Response;
import com.github.gustavopm1.gotcamel.models.themoviedb.movie.Movie;
import com.github.gustavopm1.gotcamel.services.TheMovieDBAbstractRequestService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.github.gustavopm1.gotcamel.GotCamelConstants.TYPE_VALUE;

@Service
@Slf4j
public class MovieSearchService extends TheMovieDBAbstractRequestService<Movie> {

    @Autowired
    @Setter
    private MovieSearchByIdService movieSearchByIdService;


    @Override
    public String getURL(Map<String, Object> headers) {
        return configuration.getServices().getMovieUrlName();
    }

    @Override
    public Map<String, String> getHeaders(Map<String, Object> headers) {
        return new HashMap<>();
    }

    public Map<String,String> getParams(Map<String, Object> params){
        Map<String,String> parameters = new HashMap<>();
        parameters.put("query",String.valueOf(params.get(TYPE_VALUE)));
        return parameters;
    }

    @Override
    protected Movie createBody(String responseBody) throws IOException {
        JsonNode json = new ObjectMapper().readTree(responseBody);
        List<Movie> movies = new ObjectMapper().readValue(json.get("results").toString(), new TypeReference<List<Movie>>(){});
        return movies.get(0);
    }

    @Override
    public Response<Movie> requestMovieDBTemplate(String parameter, Map<String, Object> headers) throws MovieNotFoundException {
        Response<Movie> movie = super.requestMovieDBTemplate(parameter, headers);
        return movieSearchByIdService.requestMovieDBTemplate(Integer.toString(movie.getBody().getId()), headers);
    }
}
