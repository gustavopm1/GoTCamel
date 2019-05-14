package com.github.gustavopm1.gotcamel.services.themoviedb.movie;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.gustavopm1.gotcamel.exceptions.themoviedb.movie.MovieNotFoundException;
import com.github.gustavopm1.gotcamel.models.Response;
import com.github.gustavopm1.gotcamel.models.themoviedb.movie.Movie;
import com.github.gustavopm1.gotcamel.models.themoviedb.movie.MovieCast;
import com.github.gustavopm1.gotcamel.services.TheMovieDBAbstractRequestService;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Body;
import org.apache.camel.Header;
import org.apache.camel.Headers;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.github.gustavopm1.gotcamel.GotCamelConstants.TYPE_VALUE;

@Service
@Slf4j
public class MovieCastService extends TheMovieDBAbstractRequestService<List<MovieCast>> {

    @Override
    public String getURL(Map<String, Object> headers) {
        return configuration.getServices().getMovieCrewCast().replace(":id", String.valueOf(headers.get(TYPE_VALUE)));
    }

    @Override
    public Map<String, String> getHeaders(Map<String, Object> headers) {
        return new HashMap<>();
    }

    public Map<String, String> getParams(Map<String, Object> params) {
        return new HashMap<>();
    }

    public Response<Movie> getMovieCastById(@Header(TYPE_VALUE) Integer id, @Body Response<Movie> movie, @Headers Map<String, Object> headers) throws MovieNotFoundException {
        Optional<List<MovieCast>> optionalMovieCastList = Optional.ofNullable(this.requestMovieDBTemplate(String.valueOf(id), headers).getBody());
        // TODO: re-think this null at the end.
        List<MovieCast> movieCasts = optionalMovieCastList.map(movieCastsList -> movieCastsList.stream().limit(3).collect(Collectors.toList())).orElse(null);
        movie.getBody().setCast(movieCasts);

        return Response.<Movie>builder()
                .found(true)
                .body(movie.getBody())
                .build();
    }

    @Override
    protected List<MovieCast> createBody(String responseBody) throws IOException {
        JsonNode json = new ObjectMapper().readTree(responseBody);
        return new ObjectMapper().readValue(json.get("cast").toString(), new TypeReference<List<MovieCast>>(){});
    }
}

